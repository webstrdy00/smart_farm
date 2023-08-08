package com.smart_farm.service;

import com.smart_farm.jwt.JwtTokenProvider;
import com.smart_farm.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {
    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    
    
    // refreshToken 삭제
    public void deleteToken(String refreshToken) {
        tokenRepository.deleteByRefreshToken(refreshToken);
    }
}
