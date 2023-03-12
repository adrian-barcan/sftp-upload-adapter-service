package ro.abarcan.sftpuploadadapterservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.sftp")
public class AppConfig {

    private String host;
    private int port;
    private String user;
    private String password;
    private String 	remoteDirectory;

}
