package soon.ready_action.domain.house.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soon.ready_action.domain.house.entity.House;

@Repository
public interface HouseJpaRepository extends JpaRepository<House, Long> {

    List<House> findByCityAndCounty(String city, String county);
}
