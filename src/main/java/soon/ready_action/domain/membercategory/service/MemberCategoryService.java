package soon.ready_action.domain.membercategory.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.category.repository.CategoryRepository;
import soon.ready_action.domain.member.entity.Member;
import soon.ready_action.domain.membercategory.entity.MemberCategory;
import soon.ready_action.domain.membercategory.repository.MemberCategoryRepository;

@RequiredArgsConstructor
@Service
public class MemberCategoryService {

    private final MemberCategoryRepository memberCategoryRepository;
    private final CategoryRepository categoryRepository;

    public void associateMemberWithCategories(List<String> categories, Member member) {
        categories.stream()
            .map(categoryRepository::findByTitle)
            .forEach(category -> saveMemberCategory(member, category));
    }

    private void saveMemberCategory(Member member, Category category) {
        MemberCategory memberCategory = MemberCategory.builder()
            .category(category)
            .member(member)
            .build();
        memberCategoryRepository.save(memberCategory);
    }
}
