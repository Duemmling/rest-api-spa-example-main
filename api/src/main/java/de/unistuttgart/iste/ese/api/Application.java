package de.unistuttgart.iste.ese.api;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.LinkedList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import antlr.collections.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // enable cross-origin resource sharing (CORS)
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // allow CORS requests for all resources and HTTP methods from the frontend origin
                registry.addMapping("/**")
                        .allowedMethods("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE")
                        .allowedOrigins("http://localhost:8000");
            }
        };
    }
}
