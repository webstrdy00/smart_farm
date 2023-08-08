package com.smart_farm.entity;

import com.smart_farm.dto.user.request.UserUpdateRequestDto;
import com.smart_farm.enums.LoginType;
import com.smart_farm.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean deleted;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean emailOtp;

    @Enumerated(EnumType.STRING)
    @Column
    private LoginType loginType;

    public void update(UserUpdateRequestDto userDto){
        this.nickname = userDto.getNickname();
        this.userRole = userDto.getUserRole();
    }

    public void setDeleted(boolean deleted){
        this.deleted = deleted;
    }
    public void setEmailOtp(boolean emailOtp){
        this.emailOtp = emailOtp;
    }

}
