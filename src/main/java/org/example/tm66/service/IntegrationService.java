package org.example.tm66.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tm66.config.UploadConfig;
import org.example.tm66.model.UserParams;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntegrationService {

    private final UploadConfig uploadConfig;
    private final HtmlGeneratorService htmlGeneratorService;
    private final FtpService ftpService;
    private final List<UserParams> userParamsList;

    public void uploadToFtp(@NotNull String user) throws IOException {
        UserParams params = getUserParams(user);
        generateHtmlAndSendToFtp(params);
    }

    public void save(@NotNull String user) {
        UserParams params = getUserParams(user);
        generateHtml(params);
    }

    private void generateHtmlAndSendToFtp(UserParams params) throws IOException {
        generateHtml(params);
        ftpService.uploadUserFile(params);
    }

    private void generateHtml(UserParams params) {
        htmlGeneratorService.generateGroupsHtml(uploadConfig.getWorkerDir() + "/" + params.getName().toLowerCase() + ".html");
    }

    private UserParams getUserParams(@NotNull String user) {
        return userParamsList.stream()
                .filter(params -> params.getName().equalsIgnoreCase(user))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Данные пользователя не найдены: " + user));
    }

}
