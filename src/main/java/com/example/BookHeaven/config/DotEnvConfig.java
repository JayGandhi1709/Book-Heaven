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
        try {
            // Ensure Dotenv loads from the current directory or custom path
            Dotenv dotenv = Dotenv.configure()
                    .directory("./") // You can also use an absolute path if needed
                    .ignoreIfMalformed()
                    .ignoreIfMissing()
                    .load();

            logger.info("Dotenv loaded successfully");
            return dotenv;

        } catch (Exception e) {
            logger.error("Failed to load .env file", e);
            throw new RuntimeException("Failed to load .env file", e);
        }
    }
}
