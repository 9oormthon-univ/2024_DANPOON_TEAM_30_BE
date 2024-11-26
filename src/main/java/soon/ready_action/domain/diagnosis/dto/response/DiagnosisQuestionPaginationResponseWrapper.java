package soon.ready_action.domain.diagnosis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Schema(description = "질문 페이징 조회 응답")
@Builder
public record DiagnosisQuestionPaginationResponseWrapper(

    @Schema(description = "질문 목록", example = """
        {
            "questionId": 3,
            "category": "주거",
            "question": "외부 지원 없이 월세나 주거비를 지불할 수 있나요?",
            "answerType": null
          }""")
    List<DiagnosisQuestionResponse> questions,

    @Schema(description = "다음 페이지 존재 여부", example = "true")
    boolean hasNext
) {

}
