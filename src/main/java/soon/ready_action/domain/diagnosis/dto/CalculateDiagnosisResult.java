package soon.ready_action.domain.diagnosis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "진단 결과 계산")
@Builder
public record CalculateDiagnosisResult(

    @Schema(description = "진단 결과 카테고리 제목", example = "주거")
    String categoryTitle,

    @Schema(description = "진단 결과 카테고리 점수", example = "10")
    int score
) {

}
