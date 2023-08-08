package com.smart_farm.dto.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDto {
    @ApiModelProperty(value = "사용자 email", example = "12345@naver.com")
    private String email;
    @ApiModelProperty(value = "사용자 password", example = "test")
    private String password;
}
