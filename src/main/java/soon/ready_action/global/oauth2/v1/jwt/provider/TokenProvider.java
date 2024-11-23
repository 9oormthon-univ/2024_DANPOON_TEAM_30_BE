package soon.ready_action.global.oauth2.v1.jwt.provider;

import static soon.ready_action.global.oauth2.v1.jwt.common.TokenExpiration.ACCESS_TOKEN;
import static soon.ready_action.global.oauth2.v1.jwt.common.TokenExpiration.REFRESH_TOKEN;
import static soon.ready_action.global.oauth2.v1.jwt.common.TokenType.AUTHORIZATION_HEADER;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import soon.ready_action.domain.member.entity.Role;
import soon.ready_action.global.oauth2.v1.jwt.dto.response.TokenResponse;

@Slf4j
@Component
public class TokenProvider {

    private final Key key;

    public TokenProvider(@Value("${spring.jwt.secretKey}") String secretKey) {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(decode);
    }

    public TokenResponse generateAllToken(Long memberId, Role role) {
        String accessToken = generateAccessToken(memberId, role);
        String refreshToken = generateRefreshToken();

        return TokenResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public TokenResponse reissueToken(Long memberId, Role role, String refreshToken) {
        if (!validateToken(refreshToken)) {
            throw new JwtException("정상적이지 않은 토큰");
        }
        String newAccessToken = generateAccessToken(memberId, role);
        String newRefreshToken = generateRefreshToken();

        return TokenResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(newRefreshToken)
            .build();
    }

    public Role getRoleFromToken(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        return Role.valueOf(claimsFromToken.get(AUTHORIZATION_HEADER.getValue(), String.class));
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            isExpiredToken(claims);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다. Token: {}", token);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT, 만료된 JWT 입니다. Token: {}", token);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT, 지원되지 않는 JWT 입니다. Token: {}", token);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 입니다. Token: {}", token);
        }
        return false;
    }

    private void isExpiredToken(Claims claims) {
        Date expiration = claims.getExpiration();

        if (expiration.before(new Date())) {
            throw new ExpiredJwtException(null, claims, "만료된 토큰입니다");
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaimsFromToken(token);

        String memberId = claims.getSubject();
        Role role = getRoleFromToken(token);
        Collection<GrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority(role.name()));

        return new UsernamePasswordAuthenticationToken(memberId, "", authorities);
    }

    private String generateAccessToken(Long memberId, Role role) {
        Date expirationDate = createExpirationDate(ACCESS_TOKEN.getExpirationTime());

        return Jwts.builder()
            .setSubject(memberId.toString())
            .claim(AUTHORIZATION_HEADER.getValue(), role)
            .setExpiration(expirationDate)
            .setIssuedAt(new Date())
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    private String generateRefreshToken() {
        Date expirationDate = createExpirationDate(REFRESH_TOKEN.getExpirationTime());

        return Jwts.builder()
            .setExpiration(expirationDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    private Date createExpirationDate(long expirationTime) {
        long currentTime = System.currentTimeMillis();
        return new Date(currentTime + expirationTime);
    }

    private Claims getClaimsFromToken(String accessToken) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(accessToken)
            .getBody();
    }
}
