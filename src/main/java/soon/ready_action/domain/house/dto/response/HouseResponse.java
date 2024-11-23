package soon.ready_action.domain.house.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "주거 응답")
@Builder
public record HouseResponse(

    @Schema(description = "주거 타입", example = "아파트")
    String type,

    @Schema(description = "주거 유형", example = "전세 / 월세")
    String rentalType, // 임대 유형 (enum)

    @Schema(description = "주거 가격 / 만단위", example = "7000")
    Long price, // 가격

    @Schema(description = "공급 면적", example = "10 | -")
    String supplyArea, // 공급 면적 spc1

    @Schema(description = "주거 전용면적", example = "18")
    Double exclusiveArea, // 전용 면적 spc2

    @Schema(description = "방향", example = "동남풍")
    String direction, // 방향

    @Schema(description = "위도", example = "37.366914")
    Double latitude, // 위도

    @Schema(description = "경도", example = "127.104573")
    Double longitude, // 경도

    @Schema(description = "부동산 특징", example = "풀옵션 대출가능 넓은 원룸")
    String features, // 부동산 특징

    @Schema(description = "부동산 이름", example = "부동산 이름")
    String realtorName, // 부동산 이름

    @Schema(description = "도로명 주소", example = "안양시 만안구 석수로 99번길 95")
    String roadAddress, // 도로명 주소

    @Schema(description = "시", example = "경기|서울")
    String city,

    @Schema(description = "구", example = "안양시|강남구")
    String county

) {

}
