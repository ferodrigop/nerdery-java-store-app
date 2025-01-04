package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test-containers")
@Testcontainers
@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

}
