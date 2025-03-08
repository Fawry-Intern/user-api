package com.fawry.user_api.repository;

import com.fawry.user_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUsername(String username);
}
