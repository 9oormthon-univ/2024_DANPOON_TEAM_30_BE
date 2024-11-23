package soon.ready_action.domain.house.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "house")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long housingId; // 주거 아이디

    private String cortarNo; // 법정동 코드
    private String rletTpNm; // 주택 유형 이름
    private String tradTpNm; // 거래 유형 이름
    private String prc; // 가격
    private String spc1; // 공급면적
    private Double spc2; // 전용면적
    private String direction; // 주택 방향
    private Double lat; // 위도
    private Double lng; // 경도
    private String atclFetrDesc; // 부동산 특징 설명
    private String rltrNm; // 부동산 이름
    private String fullAddress; // 전체 주소
    private String region1depthName; // 시/도
    private String region2depthName; // 시/군/구
    private String roadAddressName; // 도로명 주소
}
