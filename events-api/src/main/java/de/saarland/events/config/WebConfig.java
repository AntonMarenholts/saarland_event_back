package de.saarland.events.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // ▼▼▼ ИЗМЕНЕНИЕ ЗДЕСЬ ▼▼▼
        registry.addMapping("/**") // Меняем "/api/**" на "/**"
                .allowedOrigins("https://saarland-event-front-hq61.vercel.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}