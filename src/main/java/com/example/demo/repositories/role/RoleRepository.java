package com.example.demo.repositories.role;

import com.example.demo.entities.order.RoleName;
import com.example.demo.entities.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    @Query("SELECT r FROM Role r WHERE r.roleName = :roleName")
    Role findRoleByRoleName(@Param("roleName") RoleName roleName);
}