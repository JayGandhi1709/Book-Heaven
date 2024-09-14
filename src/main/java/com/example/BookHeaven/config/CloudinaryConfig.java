package com.example.BookHeaven.config;

import com.cloudinary.Cloudinary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class CloudinaryConfig {

    @Autowired
    private Dotenv dotenv;

    // public CloudinaryConfig(Dotenv dotenv) {
    // this.dotenv = dotenv;
    // }

    @Bean
    public Cloudinary cloudinary() {

        String CLOUD_NAME = dotenv.get("CLOUD_NAME");
        String API_KEY = dotenv.get("API_KEY");
        String API_SECRET = dotenv.get("API_SECRET");
        Map<String, Object> config = new HashMap<>();

        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);
        config.put("secure", true);

        return new Cloudinary(config);
    }
}