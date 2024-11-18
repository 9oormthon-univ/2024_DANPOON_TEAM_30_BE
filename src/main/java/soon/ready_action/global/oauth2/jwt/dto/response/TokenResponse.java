package soon.ready_action.global.oauth2.jwt.dto.response;

import lombok.Builder;

@Builder
public record TokenResponse(
    String accessToken,
    String refreshToken
) {

}
