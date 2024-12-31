package com.example.demo.configurations;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.sendgrid")
public record SendGridConfigurationProperties(
        @NotBlank
        @Pattern(regexp = "^SG[0-9a-zA-Z._]{67}$")
        String apiKey,

        @Email
        @NotBlank
        String fromEmail,

        @NotBlank
        String fromName
) {
}
