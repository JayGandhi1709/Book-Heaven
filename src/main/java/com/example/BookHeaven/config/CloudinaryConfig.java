package com.example.BookHeaven.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class CloudinaryConfig {

    private final Dotenv dotenv;

    // Use Dotenv for local development, fallback to System.getenv for production
    public CloudinaryConfig() {
        Dotenv tempDotenv = null;
        try {
            tempDotenv = Dotenv.configure().ignoreIfMissing().load(); // Load dotenv only for local dev
        } catch (Exception e) {
            // Log or handle exception as needed
        }
        this.dotenv = tempDotenv;
    }

    @Bean
    public Cloudinary cloudinary() {
        String CLOUD_NAME = getEnvVar("CLOUD_NAME");
        String API_KEY = getEnvVar("API_KEY");
        String API_SECRET = getEnvVar("API_SECRET");

        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);
        config.put("secure", true);

        return new Cloudinary(config);
    }

    // Helper method to fetch environment variable from Dotenv or System.getenv
    private String getEnvVar(String key) {
        if (dotenv != null) {
            return dotenv.get(key, System.getenv(key)); // Try Dotenv first, fallback to System.getenv
        } else {
            return System.getenv(key); // Use System.getenv in production
        }
    }
}
