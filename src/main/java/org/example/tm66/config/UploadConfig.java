package org.example.tm66.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;

@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "upload")
@Validated
public class UploadConfig {

    @NotEmpty(message = "commentDir не может быть пустым")
    private String commentDir;

    @NotEmpty(message = "workerDir не может быть пустым")
    private String workerDir;

    @PostConstruct
    void init() {
        log.info("[INIT] Upload dirs. comment: {}, worker: {}", commentDir, workerDir);
    }

}
