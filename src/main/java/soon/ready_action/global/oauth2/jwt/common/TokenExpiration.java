package soon.ready_action.global.oauth2.jwt.common;

import lombok.Getter;

@Getter
public enum TokenExpiration {
    ACCESS_TOKEN(30 * 60 * 1000L), // 30분
    REFRESH_TOKEN(7 * 24 * 60 * 60 * 1000L); // 1주일

    private final long expirationTime;

    TokenExpiration(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}