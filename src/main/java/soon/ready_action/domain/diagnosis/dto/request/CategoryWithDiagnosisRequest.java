package soon.ready_action.domain.diagnosis.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record CategoryWithDiagnosisRequest(

    @NotNull(message = "필수 입력 항목입니다")
    Map<Long, Boolean> questionResult
) {

}
