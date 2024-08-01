package com.example.BookHeaven.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.BookHeaven.controller.UserController;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
   @Bean
   public SecurityFilterChain  securityFilterChain(HttpSecurity http) throws Exception {
	   
	 http.csrf(t->t.disable()).authorizeHttpRequests(authorizeRequests -> 
       authorizeRequests
       .requestMatchers("/api/**").permitAll());
	 
	 return http.build();
	   
   }
}
