package soon.ready_action.domain.category.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.ready_action.domain.category.entity.Category;

@RequiredArgsConstructor
@Repository
public class CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    public void saveAll(List<Category> categories) {
        categoryJpaRepository.saveAll(categories);
    }

    public long count() {
        return categoryJpaRepository.count();
    }
}
