package com.example.BookHeaven.config;

import io.github.cdimascio.dotenv.Dotenv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotEnvConfig {
    private static final Logger logger = LoggerFactory.getLogger(DotEnvConfig.class);

    @Bean
    public Dotenv dotenv() {

        // Ensure that Dotenv is loaded and returned
        Dotenv dotenv = Dotenv.configure().directory("./").load();
        if (dotenv == null) {
            logger.error("Dotenv failed to load");
        } else {
            logger.info("Dotenv loaded successfully");
        }
        return dotenv;
    }
}
