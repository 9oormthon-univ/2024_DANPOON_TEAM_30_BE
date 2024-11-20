package soon.ready_action.domain.membercategory.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.ready_action.domain.membercategory.entity.MemberCategory;

@RequiredArgsConstructor
@Repository
public class MemberCategoryRepository {

    private final MemberCategoryJpaRepository memberCategoryJpaRepository;

    public void save(MemberCategory memberCategory) {
        memberCategoryJpaRepository.save(memberCategory);
    }
}
