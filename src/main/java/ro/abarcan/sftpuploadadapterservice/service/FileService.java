package ro.abarcan.sftpuploadadapterservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final SftpService sftpService;

    public void uploadFile(final MultipartFile file, final String path) {
        log.info("Uploading file {} to SFTP server.", file.getName());
        sftpService.uploadFile(file, path);
        log.info("File Successfully uploaded to SFTP server.");
    }

}
