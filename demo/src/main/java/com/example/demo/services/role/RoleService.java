package com.example.demo.services.role;

import com.example.demo.entities.order.RoleName;
import com.example.demo.entities.user.Role;
import com.example.demo.repositories.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRoleByRoleName(RoleName roleName) {
        return roleRepository.findRoleByRoleName(roleName);
    }
}
