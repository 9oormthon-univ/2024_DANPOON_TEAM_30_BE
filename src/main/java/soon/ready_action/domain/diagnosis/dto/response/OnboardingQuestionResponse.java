package soon.ready_action.domain.diagnosis.dto.response;

import lombok.Builder;

@Builder
public record OnboardingQuestionResponse(

    String content,

    String category
) {

}
