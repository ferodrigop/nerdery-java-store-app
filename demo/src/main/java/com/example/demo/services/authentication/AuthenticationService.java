package com.example.demo.services.authentication;

import com.example.demo.dtos.auth.SignInRequestDto;
import com.example.demo.dtos.auth.SignInResponseDto;
import com.example.demo.dtos.auth.SignUpRequestDto;
import com.example.demo.entities.order.RoleName;
import com.example.demo.entities.refresh_token.RefreshToken;
import com.example.demo.entities.user.User;
import com.example.demo.repositories.user.UserRepository;
import com.example.demo.services.jwt.JwtService;
import com.example.demo.services.refresh_token.RefreshTokenService;
import com.example.demo.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public SignInResponseDto login(SignInRequestDto signInRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequestDto.email(),
                        signInRequestDto.password()
                )
        );
        String accessToken = jwtService.generateAccessToken(signInRequestDto.email());
        User user = (User) authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.createNewRefreshToken(user);
        return SignInResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getId())
                .build();
    }

    @Transactional
    public UUID signUp(SignUpRequestDto signUpRequestDto, RoleName roleName) {
        User newUser = userService.createNewUser(signUpRequestDto, roleName);
        return newUser.getId();
    }

    @Transactional
    public void signOut(UUID refreshTokenId) {
        refreshTokenService.deleteRefreshTokenById(refreshTokenId);
    }

    @Transactional
    public SignInResponseDto refreshToken(UUID refreshTokenId) {
        RefreshToken refreshToken = refreshTokenService.findRefreshTokenByIdAndExpiresAtAfter(
                refreshTokenId, Instant.now()
        );
        final String newAccessToken = jwtService.generateAccessToken(refreshToken.getUser().getEmail());
        return SignInResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken.getId())
                .build();
    }
}
