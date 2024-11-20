package soon.ready_action.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import soon.ready_action.domain.diagnosis.dto.response.OnboardingQuestionResponse;
import soon.ready_action.global.oauth2.jwt.dto.response.TokenResponse;

@Schema(description = "사용자 관심 카테고리 선택에 따른 response")
@Builder
public record OnboardingWrapperResponse(

    @Schema(description = "토큰 response")
    TokenResponse tokenResponse,

    @Schema(description = "관심 카테고리 질문 List")
    List<OnboardingQuestionResponse> onboardingQuestionResponse
) {

}
