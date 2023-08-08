package com.smart_farm.repository;

import com.smart_farm.entity.Token;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
public interface TokenRepository extends JpaRepository<Token, String> {
    void deleteByRefreshToken(String refreshToken);

    @Modifying
    @Query("DELETE FROM Token t where t.expirationDate < :now")
    void deleteByExpirationDate(@Param("now")LocalDateTime now);
}
