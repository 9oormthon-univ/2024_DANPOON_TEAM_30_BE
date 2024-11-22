package soon.ready_action.global.loader;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.category.repository.CategoryRepository;

@RequiredArgsConstructor
@Service
public class CategoryLoader {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void loadCategory() {
        if (categoryRepository.count() == 0) {
            List<Category> categories = List.of(
                new Category("건강"),
                new Category("취업"),
                new Category("주거"),
                new Category("금융"),
                new Category("교육")
            );
            categoryRepository.saveAll(categories);
        }
    }

}
