package com.smart_farm.repository;

import com.smart_farm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailAndDeletedAndEmailOtp(String email, boolean deleted, boolean emailOtp);
    boolean existsByEmailAndDeletedIsTrue(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndEmailOtpIsFalse(String email);

    Optional<User> findByEmail(String email);
}
