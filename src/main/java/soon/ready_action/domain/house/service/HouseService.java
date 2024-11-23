package soon.ready_action.domain.house.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soon.ready_action.domain.house.dto.response.HouseResponse;
import soon.ready_action.domain.house.entity.House;
import soon.ready_action.domain.house.repository.HouseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository houseRepository;

    public List<HouseResponse> getHousesByRegion(String region1depthName, String region2depthName) {
        List<House> houses = houseRepository.findByRegion1depthNameAndRegion2depthName(region1depthName, region2depthName);
        return houses.stream()
                .map(house -> new HouseResponse(
                        house.getHousingId(),
                        house.getCortarNo(),
                        house.getRletTpNm(),
                        house.getTradTpNm(),
                        house.getPrc(),
                        house.getSpc1(),
                        house.getSpc2(),
                        house.getDirection(),
                        house.getLat(),
                        house.getLng(),
                        house.getAtclFetrDesc(),
                        house.getRltrNm(),
                        house.getFullAddress(),
                        house.getRoadAddressName()
                ))
                .collect(Collectors.toList());
    }
}
