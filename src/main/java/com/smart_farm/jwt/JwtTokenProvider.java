package com.smart_farm.jwt;

import com.smart_farm.enums.UserRole;
import com.smart_farm.error.ErrorCode;
import com.smart_farm.error.exception.ForbiddenException;
import com.smart_farm.repository.UserRepository;
import com.smart_farm.service.Jwt.CustomUserDetailService;
import com.smart_farm.service.Jwt.SchedulerService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class JwtTokenProvider {
    private final UserRepository userRepository;
    private final CustomUserDetailService customUserDetailService;
    private final SchedulerService schedulerService;

    // 키
    @Value("${jwt.secret}")
    private String secretKey;

    // 엑세스 토큰 유효시간 | 1h
    @Value("${jwt.accessTokenExpiration}")
    private long accessTokenValidTime;

    // 리프레시 토큰 유효시간 | 7d
    @Value("${jwt.refreshTokenExpiration}")
    private long refreshTokenValidTime;

    // 객체 초기화, secretkey를 Base64로 인코딩
    @PostConstruct // 의존성 주입 후, 초기화를 수행
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Access Token 생성
    public String createAccessToken(String email, UserRole userRole){
        return this.createToken(email, userRole, accessTokenValidTime);
    }
    
    // Refresh Token 생성
    public String createRefreshToken(String email, UserRole userRole){
        return this.createToken(email, userRole, refreshTokenValidTime);
    }
    
    // Create token
    private String createToken(String email, UserRole userRole, long tokenValid) {
        Claims claims = Jwts.claims().setSubject(email); // claims 생성 및 payload 설정
        claims.put("roles", userRole.toString()); // 권한 성정, key/ value 쌍으로 저장

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Date date = new Date();
        
        return Jwts.builder()
                .setClaims(claims) // 발행 유저 정보 저장
                .setIssuedAt(date) // 발행 시간 저장
                .setExpiration(new Date(date.getTime() + tokenValid)) // 토큰 유효 시간 저장
                .signWith(key, SignatureAlgorithm.HS256) // 해싱 알고리즘 및 키 설정
                .compact(); // 생성
    }

    // JWT 토큰에서 인증 정보 조회
    public UsernamePasswordAuthenticationToken getAuthentication(String token){
        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserEmail(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build();

        return jwtParser.parseClaimsJws(token).getBody().getSubject();
    }
    // accessToken 재발행
    public String reissueAccessToken(String refreshToken){
        String email = this.getUserEmail(refreshToken);
        if(email == null){
            throw new ForbiddenException("401", ErrorCode.ACCESS_DENIED_EXCEPTION);
        }
        return createAccessToken(email, userRepository.findByEmail(email).get().getUserRole());
    }
    // refreshToken 재발행
    public String reissueRefreshToken(String refreshToken){
        String email = this.getUserEmail(refreshToken);
        if (email == null){
            throw new ForbiddenException("401", ErrorCode.ACCESS_DENIED_EXCEPTION);
        }
        String newRefreshToken = createRefreshToken(email, userRepository.findByEmail(email).get().getUserRole());

        return newRefreshToken;
    }

    // Request의 Header에서 AccessToken 값을 가져옵니다. "authoruzation : "token"
    public String resolveAccessToken(HttpServletRequest request){
        if(request.getHeader("authorization") != null)
            return request.getHeader("authorization").substring(7);
        return null;
    }

    // Request의 Header에서 RefreshToken 값을 가져옵니다. "refreshToken : "token"
    public String resolveRefreshToken(HttpServletRequest request){
        if(request.getHeader("refreshToken") != null)
            return request.getHeader("refreshToken").substring(7);
        return null;
    }

    // Expire Token
    public void expireToken(String token){
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Date expiration = claims.getExpiration();
        Date now = new Date();
        if(now.after(expiration)){
            schedulerService.deleteExpiredToken();
        }
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken){
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);

            return !claims.getBody().getExpiration().before(new Date());
        }catch (MalformedJwtException e) {
            throw new MalformedJwtException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, "Token has expired");
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT claims string is empty");
        } catch (SignatureException e) {
            throw new SignatureException("JWT signature does not match");
        }
    }

    // 어세스 토큰 헤더 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken){
        response.setHeader("authorization", "bearer "+ accessToken);
    }

    // 리프레시 토큰 헤더 설정
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken){
        response.setHeader("refreshToken", "bearer "+ refreshToken);
    }

//    // RefreshToken 존재유뮤 확인
//    public boolean existsRefreshToken(String refreshToken){
//        return
//    }
}
