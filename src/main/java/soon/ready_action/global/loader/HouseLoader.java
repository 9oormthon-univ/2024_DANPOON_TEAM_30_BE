package soon.ready_action.global.loader;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.house.dto.HouseDataLoadDto;
import soon.ready_action.domain.house.entity.House;
import soon.ready_action.domain.house.entity.RentalType;
import soon.ready_action.domain.house.repository.HouseRepository;
import soon.ready_action.global.provider.CustomObjectMapperProvider;

@RequiredArgsConstructor
@Service
public class HouseLoader {

    private final HouseRepository houseRepository;
    private final CustomObjectMapperProvider objectMapperProvider;

    @Transactional
    public void loadHouse() {
        if (houseRepository.count() == 0) {
            List<HouseDataLoadDto> houseDataLoadDtos = objectMapperProvider.loadHouseDataFromJson();

            houseDataLoadDtos.forEach(dto -> {
                    House house = House.builder()
                        .type(dto.rletTpNm())
                        .rentalType(RentalType.of(dto.tradTpNm()))
                        .price(Long.valueOf(dto.prc()))
                        .supplyArea(dto.spc1())
                        .exclusiveArea(Double.valueOf(dto.spc2()))
                        .direction(dto.direction())
                        .latitude(dto.lat())
                        .longitude(dto.lng())
                        .features(dto.atclFetrDesc())
                        .realtorName(dto.rltrNm())
                        .roadAddress(dto.road_address_name())
                        .city(dto.region_1depth_name())
                        .county(dto.region_2depth_name())
                        .build();

                    houseRepository.save(house);
                }
            );
        }
    }
}
