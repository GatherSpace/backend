package com.hari.gatherspace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Apply to all endpoints
                        .allowedOrigins("http://localhost:5173") // Allowed origins
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                        .allowedHeaders("Content-Type", "Authorization") // Allowed request headers
                        .exposedHeaders("Custom-Header") // Headers exposed to the client
                        .allowCredentials(true) // Allow sending of cookies
                        .maxAge(3600); // Cache preflight response for 1 hour
            }
        };
    }
}