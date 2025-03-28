package com.fawry.user_api.repository;

import com.fawry.user_api.entity.User;
import com.fawry.user_api.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.classfile.instruction.InvokeInstruction;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

   Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);

    InvokeInstruction findByRole(@NotNull UserRole role);
}
