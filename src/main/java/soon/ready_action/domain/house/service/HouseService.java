package soon.ready_action.domain.house.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soon.ready_action.domain.house.dto.response.HouseResponse;
import soon.ready_action.domain.house.entity.House;
import soon.ready_action.domain.house.repository.HouseRepository;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository houseRepository;

    public List<HouseResponse> getHouseByCityAndCounty(String city, String county) {
        List<House> houses = houseRepository.findByCityAndCounty(
            city, county
        );

        return houses.stream()
            .map(HouseResponse::of)
            .toList();
    }
}
