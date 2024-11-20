package soon.ready_action.domain.auth.dto.response;

import java.util.List;
import lombok.Builder;
import soon.ready_action.domain.diagnosis.dto.response.OnboardingQuestionResponse;
import soon.ready_action.global.oauth2.jwt.dto.response.TokenResponse;

@Builder
public record OnboardingWrapperResponse(

    TokenResponse tokenResponse,

    List<OnboardingQuestionResponse> onboardingQuestionResponse
) {

}
