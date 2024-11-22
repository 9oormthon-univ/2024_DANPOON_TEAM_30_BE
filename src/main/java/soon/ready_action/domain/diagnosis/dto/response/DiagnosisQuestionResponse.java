package soon.ready_action.domain.diagnosis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "질문 페이징 조회 응답")
@Builder
public record DiagnosisQuestionResponse(

    @Schema(description = "질문 ID", example = "1")
    Long questionId,

    @Schema(description = "질문 카테고리", example = "주거")
    String category,

    @Schema(description = "질문 내용", example = "질문 내용")
    String question,

    @Schema(description = "질문 타입", example = "null | true | false")
    Boolean answerType

) {

}
