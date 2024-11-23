package soon.ready_action.domain.diagnosis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import soon.ready_action.domain.badge.entity.BadgeType;
import soon.ready_action.domain.diagnosis.dto.CalculateDiagnosisResult;

import java.util.List;

@Schema(description = "자가진단 결과")
@Builder
public record DiagnosisResultDTO(

        @Schema(description = "캐릭터 타입")
        String characterType,

        @Schema(description = "진단 결과 리스트")
        List<CalculateDiagnosisResult> results,

        @Schema(description = "뱃지 리스트")
        List<BadgeType> badges
) {
}
