package soon.ready_action.domain.diagnosis.dto;

import java.util.Map;
import lombok.Builder;

@Builder
public record QuestionDataLoadDto(

    String category,

    String onboardingQuestion,

    Map<String, Boolean> questions
) {

}
