package com.example.demo.services.aws;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Testcontainers
@SpringBootTest
class S3ServiceIntegrationTest {
    @Container
    static LocalStackContainer localStack = new LocalStackContainer(DockerImageName.parse("localstack/localstack"))
            .withServices(LocalStackContainer.Service.S3);

    @BeforeAll
    public static void setUp() throws Exception {
        S3Client s3 = S3Client
                .builder()
                .endpointOverride(localStack.getEndpoint())
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(localStack.getAccessKey(), localStack.getSecretKey())
                        )
                )
                .region(Region.of(localStack.getRegion()))
                .build();
    }

    @Test
    void uploadFile() {
    }

    @Test
    void updateObject() {
    }

    @Test
    void deleteObject() {
    }

    @Test
    void verifyObjectExists() {
    }

    @Test
    void getObjectUrl() {
    }

    @Test
    void extractKeyFromUrl() {
    }
}