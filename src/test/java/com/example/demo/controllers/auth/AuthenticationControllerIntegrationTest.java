package com.example.demo.controllers.auth;

import com.example.demo.dtos.auth.SignUpRequestDto;
import com.example.demo.entities.order.RoleName;
import com.example.demo.entities.user.Role;
import com.example.demo.entities.user.User;
import com.example.demo.repositories.role.RoleRepository;
import com.example.demo.repositories.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@ActiveProfiles("test-containers")
@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static User user;

    @BeforeAll
    public static void setup(
            @Autowired UserRepository userRepository,
            @Autowired PasswordEncoder passwordEncoder,
            @Autowired RoleRepository roleRepository
    ) {
        Role roleUser = new Role();
        roleUser.setRoleName(RoleName.ROLE_USER);
        roleUser.setPermissions(Set.of());
        roleRepository.saveAndFlush(roleUser);
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword(passwordEncoder.encode("String1234$"));
        user.setRole(roleUser);
        userRepository.saveAndFlush(user);
    }

    @Test
    void signUp() throws Exception {
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .firstName("Alan")
                .lastName("Doe")
                .email("alan.doe@example.com")
                .password("String1234$")
                .build();
        String requestBody = objectMapper.writeValueAsString(signUpRequestDto);
        MvcResult result = mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();
        assertNotNull(result.getResponse().getHeader("Location"));
    }

//    @Test
//    void signIn() {
//    }
//
//    @Test
//    void refreshToken() {
//    }
//
//    @Test
//    void signOut() {
//    }
//
//    @Test
//    void forgotPassword() {
//    }
//
//    @Test
//    void resetPassword() {
//    }
//
//    @Test
//    void changePassword() {
//    }
}