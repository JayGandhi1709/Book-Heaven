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
//	Dotenv dotenv = Dotenv.load();
	
//    private final String CLOUD_NAME = dotenv.get("CLOUD_NAME");
//    private final String API_KEY = dotenv.get("API_KEY");
//    private final String API_SECRET = dotenv.get("API_SECRET");
    
    @Bean
    public Cloudinary cloudinary(){
        Map<String, Object> config = new HashMap<>();
        
        config.put("cloud_name","book-heaven");
        config.put("api_key","418369316196387");
        config.put("api_secret","AbW0HsXW6cwzjBw2oxblMAl2jXs");
//        config.put("cloud_name",CLOUD_NAME);
//        config.put("api_key",API_KEY);
//        config.put("api_secret",API_SECRET);
        config.put("secure", true);

        return new Cloudinary(config);
    }
}