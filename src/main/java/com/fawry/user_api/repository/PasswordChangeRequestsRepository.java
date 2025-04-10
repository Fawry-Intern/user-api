package com.fawry.user_api.repository;

import com.fawry.user_api.entity.PasswordChangeRequests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordChangeRequestsRepository extends JpaRepository<PasswordChangeRequests, Long> {

    Optional<PasswordChangeRequests> findByToken(String token);
}
