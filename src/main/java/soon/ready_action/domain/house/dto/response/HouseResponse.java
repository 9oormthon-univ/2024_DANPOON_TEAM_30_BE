package soon.ready_action.domain.house.dto.response;

import lombok.Builder;

@Builder
public record HouseResponse(

    String type,

    String rentalType, // 임대 유형 (enum)

    Long price, // 가격

    String supplyArea, // 공급 면적 spc1

    Double exclusiveArea, // 전용 면적 spc2

    String direction, // 방향

    Double latitude, // 위도

    Double longitude, // 경도

    String features, // 부동산 특징

    String realtorName, // 부동산 이름

    String roadAddress, // 도로명 주소

    String city,

    String county

) {

}
