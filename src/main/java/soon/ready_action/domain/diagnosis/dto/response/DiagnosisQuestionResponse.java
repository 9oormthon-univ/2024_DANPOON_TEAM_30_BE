package soon.ready_action.domain.diagnosis.dto.response;

import lombok.Builder;

@Builder
public record DiagnosisQuestionResponse(

    Long questionId,

    String category,

    String question,

    String answerType

) {

}
