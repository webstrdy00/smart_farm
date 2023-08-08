package com.smart_farm.dto.user.request;

import com.smart_farm.entity.User;
import com.smart_farm.enums.LoginType;
import com.smart_farm.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequestDto {
    private String email;
    private String password;
    private String nickname;
    private UserRole userRole;
    private LoginType loginType;

    public User toEntity(){
        User user= User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .userRole(userRole)
                .loginType(loginType)
                .deleted(false)
                .build();

        return user;
    }
}
