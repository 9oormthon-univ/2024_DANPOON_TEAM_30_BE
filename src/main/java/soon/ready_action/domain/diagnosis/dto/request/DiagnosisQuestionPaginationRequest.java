package soon.ready_action.domain.diagnosis.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record DiagnosisQuestionPaginationRequest(

    Long lastQuestionId,

    @NotEmpty(message = "카테고리는 필수 값입니다.")
    String categoryTitle
) {

}
