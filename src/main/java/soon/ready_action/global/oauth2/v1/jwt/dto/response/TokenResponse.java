package soon.ready_action.global.oauth2.v1.jwt.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "토큰 응답")
@Builder
public record TokenResponse(

    @Schema(description = "새로운 액세스 토큰", example = "new-access-token")
    String accessToken,

    @Schema(description = "새로운 리프레시 토큰", example = "new-refresh-token")
    String refreshToken
) {

}
