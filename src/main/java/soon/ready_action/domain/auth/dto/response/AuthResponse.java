package soon.ready_action.domain.auth.dto.response;

import lombok.Builder;

@Builder
public record AuthResponse(

    String accessToken,
    String refreshToken,
    String redirectURL
) {

}
