package soon.ready_action.domain.diagnosis.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

@Schema(description = "자가진단 질문 제출")
public record CategoryWithDiagnosisRequest(

    @Schema(description = "선택한 질문의 응답/ min=1, max=5", required = true, example = "{1:true, 2:false, 3:true}")
    @NotNull(message = "필수 입력 항목입니다")
    Map<Long, Boolean> questionResult
) {

}
