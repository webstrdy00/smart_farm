package com.smart_farm.dto.user.response;

import com.smart_farm.enums.LoginType;
import com.smart_farm.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponseDto {
    private String nickname;
    private LoginType loginType;
    private UserRole userRole;
}
