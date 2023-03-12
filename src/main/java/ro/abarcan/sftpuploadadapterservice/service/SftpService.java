package ro.abarcan.sftpuploadadapterservice.service;

import com.jcraft.jsch.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.abarcan.sftpuploadadapterservice.config.SftpConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
@Service
public class SftpService {

    public static final String PATH_DELIMITER = "/";
    private final SftpConfig sftpConfig;

    public void uploadFile(final MultipartFile file, final String path) {

        Session session = null;
        ChannelSftp channelSftp = null;
        InputStream inputStream = null;

        try {
            var properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");

            final var jsch = new JSch();
            session = jsch.getSession(sftpConfig.getUser(), sftpConfig.getHost(), sftpConfig.getPort());
            session.setPassword(sftpConfig.getPassword());
            session.setConfig(properties);
            session.connect();

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            inputStream = file.getInputStream();
            channelSftp.put(inputStream, path + PATH_DELIMITER + file.getOriginalFilename(), ChannelSftp.OVERWRITE);

        } catch (JSchException e) {
            log.error("SFTP connection error!", e);
        } catch (SftpException | IOException e) {
            log.error("File upload exception!", e);
        } finally {
            closeSftpConnection(session, channelSftp);
            handleInputStreamClose(inputStream);
        }
    }

    private static void closeSftpConnection(final Session session,
                                            final ChannelSftp channelSftp) {
        if (session != null && channelSftp != null) {
            channelSftp.disconnect();
        }
    }

    private static void handleInputStreamClose(final InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error("Input stream close exception!", e);
            }
        }
    }
}