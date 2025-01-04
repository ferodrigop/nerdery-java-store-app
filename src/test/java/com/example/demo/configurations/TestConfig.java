package com.example.demo.configurations;

import com.example.demo.loaders.DataInitializer;
import com.example.demo.services.aws.S3Service;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.mock;

@ActiveProfiles("test-containers")
@TestConfiguration
public class TestConfig {
    @Bean
    public SendGridConfigurationProperties sendGridConfigurationProperties() {
        return new SendGridConfigurationProperties(
                "dummy-api-key",
                "test@mail.com",
                "Test Name"
        );
    }

    @Bean
    public S3Config s3Config() {
        return mock(S3Config.class);
    }

    @Bean
    public S3Service s3Service() {
        return mock(S3Service.class);
    }

    @Bean
    public DataInitializer dataInitializer() {
        return mock(DataInitializer.class);
    }

    @Bean
    public Email fromEmail() {
        return mock(Email.class);
    }

}
