package soon.ready_action.domain.house.dto;

import lombok.Builder;

@Builder
public record HouseDataLoadDto(
    String rletTpNm, // 원룸 등
    String tradTpNm, //전세 월세
    String prc, // 가격
    String spc1, // 공금 면적 1
    String spc2, // 전용 면적
    String direction, // 설명
    Double lat, // 위도
    Double lng, // 경도
    String atclFetrDesc, //부동상 특징
    String rltrNm, //부동산 이 름
    String road_address_name, // 도로명 주소
    String region_1depth_name,
    String region_2depth_name,
    String region_3depth_name
) {

}
