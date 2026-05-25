package org.example.tm66.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "app.ftp")
@Validated
public class FtpParams {

    @NotBlank(message = "host не может быть пустым")
    private String host;

    @NotNull(message = "port не может быть пустым")
    @Positive(message = "port должен быть положительным")
    private Integer port;

    @NotBlank(message = "user не может быть пустым")
    private String user;

    @NotBlank(message = "password не может быть пустым")
    private String password;

    @PostConstruct
    void init() {
        log.info("[INIT] FTP {}:{} {}/{}", host, port, user, password);
    }

}
