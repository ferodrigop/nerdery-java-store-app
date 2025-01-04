package com.example.demo.utils;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class StartupEnvValidator {

    private static final Dotenv dotenv = Dotenv.load();

    @PostConstruct
    public void validateEnvironmentVariables() {
        try {
            EnvValidator.validateEnvVars();
        } catch (Exception e) {
            throw new IllegalStateException("Environment variable validation failed: " + e.getMessage(), e);
        }
    }
}
