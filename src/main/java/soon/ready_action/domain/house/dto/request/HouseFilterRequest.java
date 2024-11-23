package soon.ready_action.domain.house.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주거 조회 요청")
public record HouseFilterRequest(

    @Schema(description = "시", example = "서울 | 경기")
    String city,

    @Schema(description = "군구", example = "성남시 분당구 | 강남구")
    String county
) {

}
