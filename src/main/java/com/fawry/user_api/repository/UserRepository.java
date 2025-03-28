package com.fawry.user_api.repository;

import com.fawry.user_api.entity.User;
import com.fawry.user_api.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

   Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);

    boolean existsByRole(UserRole userRole);
}
