package org.example.tm66;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class Tm66Application {

	public static void main(String[] args) {
		SpringApplication.run(Tm66Application.class, args);
	}

}
