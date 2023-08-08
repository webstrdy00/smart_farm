package com.smart_farm.service;

import com.smart_farm.dto.user.request.UserLoginRequestDto;
import com.smart_farm.dto.user.request.UserSignUpRequestDto;
import com.smart_farm.dto.user.response.UserLoginResponseDto;
import com.smart_farm.dto.user.response.UserProfileResponseDto;
import com.smart_farm.entity.User;
import com.smart_farm.enums.LoginType;
import com.smart_farm.enums.UserRole;
import com.smart_farm.error.ErrorCode;
import com.smart_farm.error.exception.UnAuthorizedException;
import com.smart_farm.jwt.JwtTokenProvider;
import com.smart_farm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.smart_farm.error.ErrorCode.ACCESS_DENIED_EXCEPTION;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;


    public UserLoginResponseDto login(UserLoginRequestDto requestDto, HttpServletResponse response) {
        if(!userRepository.existsByEmailAndDeletedAndEmailOtp(requestDto.getEmail(), false, true)){
            if (!userRepository.existsByEmail(requestDto.getEmail())){
                return UserLoginResponseDto.builder()
                        .responseCode("2001")  // 회원이 아닌 경우
                        .build();
            }
            else if(userRepository.existsByEmailAndDeletedIsTrue(requestDto.getEmail())){
                return UserLoginResponseDto.builder()
                        .responseCode("2002") // 탈퇴한 회원인 경우
                        .build();
            } else if (userRepository.existsByEmailAndEmailOtpIsFalse(requestDto.getEmail())) {
                userRepository.delete(userRepository.findByEmail(requestDto.getEmail()).orElseThrow());
                return UserLoginResponseDto.builder()
                        .responseCode("2003") // 2차 인증이 제대로 이루어지지 않은 경우 -> 처음부터 회원 가입 진행
                        .build();
            }
        }

        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow();

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new UnAuthorizedException("401", ACCESS_DENIED_EXCEPTION); // 패스워드 불일치
        }

        this.setJwtTokenHeader(requestDto.getEmail(), response);

        return UserLoginResponseDto.builder()
                .responseCode("200")
                .build();
    }

    public void signUp(UserSignUpRequestDto requestDto, HttpServletResponse response) {
        if(userRepository.existsByEmail(requestDto.getEmail())){  // 이메일이 있으면 에러 던짐
            throw new UnAuthorizedException("401", ACCESS_DENIED_EXCEPTION);
        }
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User user = requestDto.toEntity();
        user.setEmailOtp(false);
        userRepository.save(user);
        this.setJwtTokenHeader(requestDto.getEmail(), response);
//        if(requestDto.getUserRole().equals(LoginType.NORMAL)){  // 일반 회원가입은 2차 인증 필요
//            requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
//            User user = requestDto.toEntity();
//            user.setEmailOtp(false);
//            userRepository.save(user);
//        }
//        else{
//            throw new UnAuthorizedException("401_NOT_ALLOW", NOT_ALLOW_WRITE_EXCEPTION);
//        }
    }
    public UserProfileResponseDto viewProfile(HttpServletRequest request) {
        User user = findUserByToken(request);

        UserProfileResponseDto responseDto = UserProfileResponseDto.builder()
                .nickname(user.getNickname())
                .loginType(user.getLoginType())
                .userRole(user.getUserRole())
                .build();

        return responseDto;
    }
    public void logout(HttpServletRequest request) {
        refreshTokenService.deleteToken(jwtTokenProvider.resolveRefreshToken(request));
        jwtTokenProvider.expireToken(jwtTokenProvider.resolveAccessToken(request));
    }
    public void withdrawalMembership(HttpServletRequest request) {
        User user = findUserByToken(request);

        user.setDeleted(true);
        this.logout(request);
    }
    // 토큰에서 정보 가져오기
    private User findUserByToken(HttpServletRequest request) {
        String email = jwtTokenProvider.getUserEmail(jwtTokenProvider.resolveAccessToken(request));
        User user = userRepository.findByEmail(email).orElseThrow();
        return user;
    }

    private void setJwtTokenHeader(String email, HttpServletResponse response) {
        UserRole userRole = userRepository.findByEmail(email).get().getUserRole();

        String accessToken = jwtTokenProvider.createAccessToken(email, userRole);
        String refreshToken = jwtTokenProvider.createRefreshToken(email, userRole);

        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);
    }



}
