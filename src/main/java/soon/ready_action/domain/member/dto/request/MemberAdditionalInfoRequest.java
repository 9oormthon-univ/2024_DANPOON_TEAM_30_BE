package soon.ready_action.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Schema(description = "로그인 후 사용자의 추가 정보 입력")
@Builder
public record MemberAdditionalInfoRequest(

    @Schema(description = "사용자 닉네임", example = "jjh75607")
    @NotEmpty(message = "닉네임은 필수 입력항목입니다.")
    String nickname,

    @Schema(description = "생년월일 (YYYY-MM-DD)", example = "2024-01-01")
    @NotNull(message = "생년월일은 필수 항목입니다.")
    @PastOrPresent(message = "생년월일은 현재 날짜 이전이어야 합니다.")
    LocalDate birthday,

    @Schema(description = "카테고리 활성 상태", example = "['건강', '주거']")
    @NotNull(message = "카테고리는 한개 이상 선택되야 합니다.")
    List<String> categories
) {

}
