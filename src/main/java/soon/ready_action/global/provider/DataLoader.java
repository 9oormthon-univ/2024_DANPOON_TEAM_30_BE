package soon.ready_action.global.provider;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.category.repository.CategoryRepository;

@RequiredArgsConstructor
@Configuration
public class DataLoader {

    private final CategoryRepository categoryRepository;

    @Transactional
    @PostConstruct
    public void loadData() {
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