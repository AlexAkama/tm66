package org.example.tm66.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.example.tm66.config.FtpParams;
import org.example.tm66.model.UserParams;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class FtpService {

    public final FtpParams ftpParams;

    public void uploadUserFile(UserParams userParams) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpParams.getHost(), ftpParams.getPort());
            ftpClient.login(ftpParams.getUser(), ftpParams.getPassword());
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            try (InputStream inputStream = Files.newInputStream(Path.of("ftp/" + userParams.getName() + ".html"))) {
                ftpClient.storeFile(userParams.getFtpPath() + "/index.html", inputStream);
            }
            log.info("Данные для {} успешно отправлены на FTP", userParams.getName());
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

}
