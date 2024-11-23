package soon.ready_action.domain.diagnosis.dto.response;

import java.util.List;
import lombok.Builder;
import soon.ready_action.domain.diagnosis.dto.CalculateDiagnosisResult;
import soon.ready_action.domain.program.dto.response.ProgramResponse.DetailResponse;

@Builder
public record DiagnosisResultWrapper(

    String characterType,

    List<CalculateDiagnosisResult> results,

    List<DetailResponse> programs
) {

}
