package com.cleaner.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class EnvConfig {
    private static final Logger logger = LoggerFactory.getLogger(EnvConfig.class);

    @PostConstruct
    public void loadEnv() {
        try {
            logger.info("Attempting to load .env file...");
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();
            
            // Load environment variables into System properties
            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
                logger.info("Loaded environment variable: {}", entry.getKey());
            });
            
            // Verify MongoDB URI is set
            String mongoUri = System.getProperty("MONGODB_URI");
            if (mongoUri == null || mongoUri.isEmpty()) {
                logger.error("MONGODB_URI is not set! Please check your .env file.");
            } else {
                logger.info("MongoDB URI is configured.");
            }
            
        } catch (Exception e) {
            logger.error("Error loading .env file: {}", e.getMessage());
            throw new RuntimeException("Failed to load environment variables", e);
        }
    }
} 