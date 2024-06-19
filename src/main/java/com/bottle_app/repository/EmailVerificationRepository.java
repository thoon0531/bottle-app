package com.bottle_app.repository;

import com.bottle_app.model.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, UUID> {

    boolean existsByUsername(String username);

    EmailVerification findByUsername(String username);
}
