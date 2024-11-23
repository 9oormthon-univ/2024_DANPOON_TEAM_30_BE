package soon.ready_action.domain.house.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.ready_action.domain.house.entity.House;

@RequiredArgsConstructor
@Repository
public class HouseRepository {

    private final HouseJpaRepository houseJpaRepository;

    public void save(House house) {
        houseJpaRepository.save(house);
    }

    public Long count() {
        return houseJpaRepository.count();
    }

    public List<House> findByCityAndCounty(String city, String county) {
        return houseJpaRepository.findByCityAndCounty(city, county);
    }
}
