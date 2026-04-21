package org.example.tm66.config;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.tm66.model.UserParams;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "user.kgs")
@Validated
public class KgsUserParams implements UserParams {

    @NotEmpty(message = "email не может быть пустым")
    private String email;

    @NotEmpty(message = "ftp-path  не может быть пустым")
    private String ftpPath;

    @PostConstruct
    void init() {
        log.info("[INIT] KGS email: {}, ftp-path: {}", email, ftpPath);
    }

    public String getName() {
        return "KGS";
    }

}
