package com.smart_farm.service.Jwt;

import com.smart_farm.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class SchedulerService {
    private final TokenRepository tokenRepository;

    // Token 만료시 데이터 삭제
    @Scheduled(fixedRate = 3600000)   // 일정 시간이 지나면 코드가 실행되게 설정
    public void deleteExpiredToken(){
        LocalDateTime now = LocalDateTime.now();

        tokenRepository.deleteByExpirationDate(now);
    }
}
