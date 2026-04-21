package org.example.tm66.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tm66.model.UserParams;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntegrationService {

    private final HtmlGeneratorService htmlGeneratorService;
    private final FtpService ftpService;
    private final List<UserParams> userParamsList;

    public void upload(@NotNull String user) throws IOException {
        UserParams userParams = getUserParams(user);
        generateHtmlAndSendToFtp(userParams);
    }

    private void generateHtmlAndSendToFtp(UserParams params) throws IOException {
        htmlGeneratorService.generateGroupsHtml("ftp/" + params.getName() + ".html");
        ftpService.uploadUserFile(params);
    }

    private UserParams getUserParams(@NotNull String user) {
        return userParamsList.stream()
                .filter(params -> params.getName().equalsIgnoreCase(user))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Данные пользователя не найдены: " + user));
    }

}
