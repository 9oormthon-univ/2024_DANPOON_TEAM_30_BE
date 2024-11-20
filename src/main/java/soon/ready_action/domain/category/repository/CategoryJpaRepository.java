package soon.ready_action.domain.category.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import soon.ready_action.domain.category.entity.Category;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByTitle(String title);

    @Query("SELECT c FROM Category c WHERE c.title IN :titles")
    List<Category> findAllByTitles(@Param("titles") List<String> titles);
}
