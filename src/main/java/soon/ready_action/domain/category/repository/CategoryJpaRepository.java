package soon.ready_action.domain.category.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import soon.ready_action.domain.category.entity.Category;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByTitle(String title);
}
