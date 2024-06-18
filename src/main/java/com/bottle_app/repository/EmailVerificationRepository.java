package com.bottle_app.repository;

import com.bottle_app.model.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, String> {

    boolean existsByUsername(String username);

    EmailVerification findByUsername(String username);
}
