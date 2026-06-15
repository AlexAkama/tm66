package org.example.tm66.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;

@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "app")
@Validated
public class AppParams {

    @NotBlank(message = "version не может быть пустым")
    private String version;

    @PostConstruct
    void init() {
        log.info("[INIT] APP. Version: {}}", version);
    }

}
