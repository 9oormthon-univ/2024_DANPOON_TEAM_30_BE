package soon.ready_action.domain.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soon.ready_action.domain.house.entity.House;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    List<House> findByRegion1depthNameAndRegion2depthName(String region1depthName, String region2depthName);
}
