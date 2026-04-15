package org.example.tm66.config;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "user.kgs")
@Validated
public class KgsUserParams {

    @NotEmpty(message = "email не может быть пустым")
    private String email;

    @PostConstruct
    void init() {
        log.info("[INIT] KGS email: {}", email);
    }

    public String getName() {
        return "KGS";
    }

}
