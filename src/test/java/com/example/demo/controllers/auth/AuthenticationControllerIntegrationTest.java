package com.example.demo.controllers.auth;

import com.example.demo.dtos.auth.*;
import com.example.demo.dtos.general.ResponseMessageDto;
import com.example.demo.entities.order.RoleName;
import com.example.demo.entities.user.Role;
import com.example.demo.entities.user.User;
import com.example.demo.repositories.role.RoleRepository;
import com.example.demo.repositories.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    private static UUID refreshToken;

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

    @AfterAll
    public static void teardown(
            @Autowired UserRepository userRepository,
            @Autowired RoleRepository roleRepository
    ) {
        userRepository.deleteAll();

        if (roleRepository != null) {
            roleRepository.deleteAll();
        }
    }

    @Order(1)
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

    @Order(2)
    @Test
    void signIn() throws Exception {
        SignInRequestDto signInRequestDto = SignInRequestDto.builder()
                .email("john.doe@example.com")
                .password("String1234$")
                .build();
        String requestBody = objectMapper.writeValueAsString(signInRequestDto);

        MvcResult result = mockMvc.perform(post("/api/v1/authentication/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        SignInResponseDto signInResponseDto = objectMapper.readValue(responseContent, SignInResponseDto.class);

        assertNotNull(signInResponseDto.accessToken());
        assertNotNull(signInResponseDto.refreshToken());

        refreshToken = signInResponseDto.refreshToken();
    }

    @Order(3)
    @Test
    void refreshToken() throws Exception {
        RefreshTokenRequestDto refreshTokenRequestDto = RefreshTokenRequestDto.builder()
                .refreshToken(refreshToken)
                .build();
        String requestBody = objectMapper.writeValueAsString(refreshTokenRequestDto);

        MvcResult result = mockMvc.perform(post("/api/v1/authentication/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        SignInResponseDto signInResponseDto = objectMapper.readValue(responseContent, SignInResponseDto.class);

        assertNotNull(signInResponseDto.accessToken());
        assertNotNull(signInResponseDto.refreshToken());

        refreshToken = signInResponseDto.refreshToken();
    }

    @Order(4)
    @Test
    void signOut() throws Exception {
        System.out.println("signOut: " + refreshToken);

        SignOutDto signOutDto = SignOutDto.builder()
                .refreshToken(refreshToken)
                .build();

        String requestBody = objectMapper.writeValueAsString(signOutDto);

        mockMvc.perform(post("/api/v1/authentication/sign-out")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Order(5)
    @Test
    void changePassword() throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities()
        );

        ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                .currentPassword("String1234$")
                .newPassword("String1234!")
                .newPasswordConfirmation("String1234!")
                .build();

        String requestBody = objectMapper.writeValueAsString(changePasswordDto);

        MvcResult result = mockMvc.perform(post("/api/v1/authentication/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(authentication(authentication)))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        ResponseMessageDto responseMessageDto = objectMapper.readValue(responseContent, ResponseMessageDto.class);

        assertNotNull(responseMessageDto.message());
    }
}