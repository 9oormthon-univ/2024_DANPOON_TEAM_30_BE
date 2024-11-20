package soon.ready_action.global.oauth2.jwt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "토큰 재발급 요청")
public record ReissueTokenRequest(

    @Schema(description = "리프레시 토큰", example = "your-refresh-token")
    @NotEmpty(message = "액세스 토큰을 입력해주세요")
    String accessToken,

    @Schema(description = "액세스 토큰", example = "your-access-token")
    @NotEmpty(message = "리프레시 토큰을 입력해주세요")
    String refreshToken
) {

}
