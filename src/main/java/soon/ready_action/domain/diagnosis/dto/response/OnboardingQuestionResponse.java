package soon.ready_action.domain.diagnosis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "관심카테고리 질문 List")
@Builder
public record OnboardingQuestionResponse(

    @Schema(description = "질문 번호", example = "1")
    Long questionId,

    @Schema(description = "질문 내용", example = "정기적으로 신체적, 정신적 관리에 힘쓰며 미래 건강 관리를 위한 계획이 세워져있나요?")
    String content,

    @Schema(description = "카테고리 제목", example = "건강")
    String category
) {

}
