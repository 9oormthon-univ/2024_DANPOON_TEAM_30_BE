package soon.ready_action.domain.membercategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soon.ready_action.domain.membercategory.entity.MemberCategory;

import java.util.List;

public interface MemberCategoryJpaRepository extends JpaRepository<MemberCategory, Long> {

    List<MemberCategory> findByMemberId(Long memberId);
}
