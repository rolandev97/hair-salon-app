package com.tp.hair_salon_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")  // autoriser cette origine
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // autoriser les méthodes HTTP
                .allowedHeaders("*")  // autoriser tous les headers
                .allowCredentials(true);  // autoriser l'envoi de cookies (si nécessaire)
    }
}
