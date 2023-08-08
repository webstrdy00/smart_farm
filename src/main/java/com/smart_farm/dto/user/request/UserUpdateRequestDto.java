package com.smart_farm.dto.user.request;

import com.smart_farm.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserUpdateRequestDto {
    private String nickname;
    private UserRole userRole;
}
