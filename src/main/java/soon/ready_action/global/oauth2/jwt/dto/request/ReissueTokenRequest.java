package soon.ready_action.global.oauth2.jwt.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record ReissueTokenRequest(

    @NotEmpty(message = "액세스 토큰을 입력해주세요")
    String accessToken,

    @NotEmpty(message = "리프레시 토큰을 입력해주세요")
    String refreshToken
) {

}
