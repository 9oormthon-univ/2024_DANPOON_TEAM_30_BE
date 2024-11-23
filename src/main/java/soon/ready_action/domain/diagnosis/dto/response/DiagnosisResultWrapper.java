package soon.ready_action.domain.diagnosis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import soon.ready_action.domain.diagnosis.dto.CalculateDiagnosisResult;
import soon.ready_action.domain.program.dto.response.ProgramResponse.DetailResponse;

@Schema(description = "진단 결과 Wrapper")
@Builder
public record DiagnosisResultWrapper(

    @Schema(description = "캐릭터 타입", example = "쿨쿨이")
    String characterType,

    @Schema(description = "진단 결과 리스트")
    List<CalculateDiagnosisResult> results,

    @Schema(description = "프로그램 리스트")
    List<DetailResponse> programs
) {

}
