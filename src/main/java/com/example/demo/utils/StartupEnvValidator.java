package com.example.demo.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class StartupEnvValidator {

    private static final Dotenv dotenv = Dotenv.load();

    public void validateEnvironmentVariables() {
        try {
            EnvValidator.validateEnvVars();
        } catch (Exception e) {
            throw new IllegalStateException("Environment variable validation failed: " + e.getMessage(), e);
        }
    }
}
