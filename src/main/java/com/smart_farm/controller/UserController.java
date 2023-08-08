package com.smart_farm.controller;

import com.smart_farm.dto.user.request.UserLoginRequestDto;
import com.smart_farm.dto.user.request.UserSignUpRequestDto;
import com.smart_farm.dto.user.response.UserLoginResponseDto;
import com.smart_farm.dto.user.response.UserProfileResponseDto;
import com.smart_farm.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "일반 로그인 API")
    @GetMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto, HttpServletResponse response){
        return userService.login(requestDto, response);
    }

    @ApiOperation(value = "회원가입 API")
    @PostMapping("/signup")
    public ResponseEntity<String> userSignUp(@RequestBody UserSignUpRequestDto requestDto, HttpServletResponse response){
        userService.signUp(requestDto, response);
        return ResponseEntity.ok("회원가입 완료.");
    }

    @ApiOperation(value = "내 정보 확인 API")
    @GetMapping("")
    public UserProfileResponseDto viewPrifile(HttpServletRequest request){
        return userService.viewProfile(request);
    }

    @ApiOperation(value = "로그아웃 API")
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request){
        userService.logout(request);
        return ResponseEntity.ok("로그아웃 되었습니다");
    }

    @ApiOperation(value = "회원 탈퇴")
    @PutMapping("/withdrawal")
    public ResponseEntity<String> withdrawalMembership(HttpServletRequest request){
        userService.withdrawalMembership(request);
        return ResponseEntity.ok("회원탈퇴");
    }
}
