package soon.ready_action.domain.house.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import soon.ready_action.domain.house.entity.House;

@Schema(description = "주거 응답")
@Builder
public record HouseResponse(

    @Schema(description = "주거 타입", example = "아파트")
    String type,

    @Schema(description = "주거 유형", example = "전세 / 월세")
    String rentalType,

    @Schema(description = "주거 가격 / 만단위", example = "7000")
    Long price,

    @Schema(description = "공급 면적", example = "10 | -")
    String supplyArea,

    @Schema(description = "주거 전용면적", example = "18")
    Double exclusiveArea,

    @Schema(description = "방향", example = "동남풍")
    String direction,

    @Schema(description = "위도", example = "37.366914")
    Double latitude,

    @Schema(description = "경도", example = "127.104573")
    Double longitude,

    @Schema(description = "부동산 특징", example = "풀옵션 대출가능 넓은 원룸")
    String features,

    @Schema(description = "부동산 이름", example = "부동산 이름")
    String realtorName,

    @Schema(description = "도로명 주소", example = "안양시 만안구 석수로 99번길 95")
    String roadAddress,

    @Schema(description = "시", example = "경기|서울")
    String city,

    @Schema(description = "구", example = "안양시|강남구")
    String county

) {

    public static HouseResponse of(House house) {
        return HouseResponse.builder()
            .type(house.getType())
            .rentalType(house.getRentalType().getType())
            .price(house.getPrice())
            .supplyArea(house.getSupplyArea())
            .exclusiveArea(house.getExclusiveArea())
            .direction(house.getDirection())
            .latitude(house.getLatitude())
            .longitude(house.getLongitude())
            .features(house.getFeatures())
            .realtorName(house.getRealtorName())
            .roadAddress(house.getRoadAddress())
            .city(house.getCity())
            .county(house.getCounty())
            .build();
    }
}
