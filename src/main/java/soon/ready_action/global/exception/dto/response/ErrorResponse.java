package soon.ready_action.global.exception.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;

@Schema(description = "에러 응답")
public record ErrorResponse(

    @Schema(description = "http 상태코드", example = "404")
    int status,

    @Schema(description = "에러 메세지", example = "존재하지 않는 회원입니다.. 등")
    String message,

    @Schema(description = "검증 오류 필드와 메시지 매핑", example = "{\"fieldName\": \"fieldName을 입력해주세요.\"}")
    Map<String, String> validation
) {

    @Builder
    public ErrorResponse(int status, String message, Map<String, String> validation) {
        this.status = status;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}

