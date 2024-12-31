package com.example.demo.services.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class S3Service {
    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    public String uploadFile(MultipartFile file, String fileKey) {
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileKey)
                            .contentType(file.getContentType())
                            .build(),
                    software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes())
            );

            return getObjectUrl(fileKey);
        } catch (S3Exception | IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }

    public String updateObject(MultipartFile file, String fileKey) {
        return this.uploadFile(file, fileKey);
    }

    public void deleteObject(String fileKey) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

    public boolean verifyObjectExists(String fileKey) {
        try {
            s3Client.headObject(HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .build());
            return true;
        } catch (Exception e) {
            return false; // Object does not exist or access denied
        }
    }

    public String getObjectUrl(String fileKey) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileKey);
    }

    public String extractKeyFromUrl(String url) {
        String prefix = String.format("https://%s.s3.%s.amazonaws.com/", bucketName, region);
        if (url.startsWith(prefix)) {
            return url.substring(prefix.length());
        }
        throw new IllegalArgumentException("Invalid URL format");
    }
}
