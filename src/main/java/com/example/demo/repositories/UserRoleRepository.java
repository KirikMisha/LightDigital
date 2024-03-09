package com.example.demo.repositories;

import com.example.demo.enums.RoleName;
import com.example.demo.models.User;
import com.example.demo.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRoleName(RoleName roleName);
}
