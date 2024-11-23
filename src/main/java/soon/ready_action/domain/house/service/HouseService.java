package soon.ready_action.domain.house.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soon.ready_action.domain.house.dto.request.HouseFilterRequest;
import soon.ready_action.domain.house.dto.response.HouseResponse;
import soon.ready_action.domain.house.entity.House;

import java.util.List;
import soon.ready_action.domain.house.repository.HouseRepository;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository houseRepository;

    public List<HouseResponse> getHouseByCityAndCounty(HouseFilterRequest request) {
        List<House> houses = houseRepository.findByCityAndCounty(
            request.city(), request.county()
        );

        return houses.stream()
            .map(house -> {
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
            }).toList();
    }
}
