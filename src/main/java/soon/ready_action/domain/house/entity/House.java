package soon.ready_action.domain.house.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "house")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    private Long id;

    private String type;

    @Enumerated(EnumType.STRING)
    private RentalType rentalType; // 임대 유형 (enum)

    private Long price; // 가격

    private String supplyArea; // 공급 면적 spc1

    private Double exclusiveArea; // 전용 면적 spc2

    private String direction; // 방향

    private Double latitude; // 위도

    private Double longitude; // 경도

    private String features; // 부동산 특징

    private String realtorName; // 부동산 이름

    private String roadAddress; // 도로명 주소

    private String city;

    private String county;

    @Builder
    public House(String type, RentalType rentalType, Long price, String supplyArea,
        Double exclusiveArea, String direction, Double latitude, Double longitude, String features,
        String realtorName, String roadAddress, String city, String county) {
        this.type = type;
        this.rentalType = rentalType;
        this.price = price;
        this.supplyArea = supplyArea;
        this.exclusiveArea = exclusiveArea;
        this.direction = direction;
        this.latitude = latitude;
        this.longitude = longitude;
        this.features = features;
        this.realtorName = realtorName;
        this.roadAddress = roadAddress;
        this.city = city;
        this.county = county;
    }
}
