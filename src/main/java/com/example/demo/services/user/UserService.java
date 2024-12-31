package com.example.demo.services.user;

import com.example.demo.dtos.auth.SignUpRequestDto;
import com.example.demo.entities.order.RoleName;
import com.example.demo.entities.user.User;
import com.example.demo.repositories.user.UserRepository;
import com.example.demo.services.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Transactional
    public User createNewUser(SignUpRequestDto signUpRequestDto, RoleName roleName) {
        User newUser = new User();
        newUser.setFirstName(signUpRequestDto.firstName());
        newUser.setLastName(signUpRequestDto.lastName());
        newUser.setEmail(signUpRequestDto.email());
        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.password()));
        newUser.setRole(roleService.getRoleByRoleName(roleName));
        newUser.setEmailVerified(true); // TODO: add email verification flow
        newUser.setDeleted(false);
        return userRepository.saveAndFlush(newUser);
    }
}
