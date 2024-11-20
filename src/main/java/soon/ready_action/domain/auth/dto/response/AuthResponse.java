package soon.ready_action.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "로그인 응답")
@Builder
public record AuthResponse(

    @Schema(description = "새로운 액세스 토큰", example = "new-access-token")
    String accessToken,

    @Schema(description = "새로운 리프레시 토큰", example = "new-refresh-token")
    String refreshToken,

    @Schema(description = "main or 추가정보", example = "/")
    String redirectURL
) {

}
