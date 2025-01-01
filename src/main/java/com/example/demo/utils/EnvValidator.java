package com.example.demo.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Optional;

public class EnvValidator {
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing() // Optional: ignores .env file if missing
            .load();

    public static void validateEnvVars() {
        validateRequired("SPRING_APPLICATION_BASE_URL");
        validateRequired("SPRING_DATASOURCE_USERNAME");
        validateRequired("SPRING_DATASOURCE_PASSWORD");
        validateRequired("SPRING_DATASOURCE_URL");
        validateRequired("SPRING_DATA_REDIS_HOST");
        validatePort("SPRING_DATA_REDIS_PORT");
        validateJWTKey("JWT_SECRET_KEY");
        validateRequired("SPRING_SENDGRID_API_KEY");
        validateRequired("SPRING_SENDGRID_FROM_EMAIL");
        validateRequired("SPRING_SENDGRID_FROM_NAME");
        validateRequired("SPRING_SENDGRID_TEMPLATE_RESET_PASSWORD_EMAIL");
        validateRequired("SPRING_AWS_S3_BUCKET_NAME");
        validateRequired("SPRING_AWS_S3_REGION");
        validateRequired("SPRING_AWS_S3_ACCESS_KEY_ID");
        validateRequired("SPRING_AWS_S3_SECRET_ACCESS_KEY");
        validateRequired("STRIPE_API_PUBLISHABLE_KEY");
        validateRequired("STRIPE_API_SECRET_KEY");
        validateRequired("STRIPE_API_WEBHOOK_SECRET");
    }

    private static void validateRequired(String key) {
        Optional<String> value = Optional.ofNullable(dotenv.get(key));
        if (value.isEmpty() || value.get().isBlank()) {
            throw new IllegalStateException("Missing required environment variable: " + key);
        }
    }

    private static void validatePort(String key) {
        String port = dotenv.get(key);
        try {
            int portValue = Integer.parseInt(port);
            if (portValue < 1 || portValue > 65535) {
                throw new IllegalArgumentException(key + " must be a valid port number (1-65535).");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(key + " must be a valid integer.");
        }
    }

    private static void validateJWTKey(String key) {
        String jwtKey = dotenv.get(key);
        if (jwtKey == null || jwtKey.length() < 16) {
            throw new IllegalStateException(key + " must be at least 16 characters long.");
        }
    }
}
