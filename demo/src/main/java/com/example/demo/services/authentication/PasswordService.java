package com.example.demo.services.authentication;

import com.example.demo.dtos.auth.ChangePasswordDto;
import com.example.demo.dtos.auth.ForgotPasswordDto;
import com.example.demo.dtos.auth.ResetPasswordDto;
import com.example.demo.entities.password_reset.PasswordReset;
import com.example.demo.entities.user.User;
import com.example.demo.exceptions.CurrentPasswordException;
import com.example.demo.exceptions.NewPasswordMismatchException;
import com.example.demo.repositories.user.UserRepository;
import com.example.demo.utils.IAuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PasswordService {
    private final PasswordEncoder passwordEncoder;
    private final IAuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final PasswordResetService passwordResetService;

    @Transactional
    public void changePassword(ChangePasswordDto changePasswordDto) {
        if (!changePasswordDto.newPassword().equals(changePasswordDto.newPasswordConfirmation())) {
            throw new NewPasswordMismatchException("The new password and its confirmation do not match");
        }
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        if (!passwordEncoder.matches(changePasswordDto.currentPassword(), user.getPassword())) {
            throw new CurrentPasswordException("Wrong password provided");
        }
        if (!passwordEncoder.matches(changePasswordDto.newPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordDto.newPassword()));
            userRepository.save(user);
        }
    }

    @Transactional
    public void initiatePasswordReset(ForgotPasswordDto forgotPasswordDto) {
        User user = userRepository.findByEmail(forgotPasswordDto.email())
                .orElse(null);
        if (Objects.nonNull(user)) {
            PasswordReset passwordReset = passwordResetService.createPasswordReset(user);
        }
        // todo: sent email with password reset uuid
    }

    @Transactional
    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        if (!resetPasswordDto.newPassword().equals(resetPasswordDto.newPasswordConfirmation())) {
            throw new NewPasswordMismatchException("The new password and its confirmation do not match");
        }
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        passwordResetService.findPasswordResetByIdAndUserAndExpiresAtAfter(
                resetPasswordDto.token(),
                user,
                Instant.now()
        );
        if (!passwordEncoder.matches(resetPasswordDto.newPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(resetPasswordDto.newPassword()));
            userRepository.save(user);
        }
    }
}
