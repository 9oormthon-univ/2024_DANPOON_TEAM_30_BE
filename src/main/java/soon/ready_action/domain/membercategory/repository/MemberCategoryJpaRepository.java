package soon.ready_action.domain.membercategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soon.ready_action.domain.membercategory.entity.MemberCategory;

import java.util.List;

public interface MemberCategoryJpaRepository extends JpaRepository<MemberCategory, Long> {

    // 특정 memberId에 해당하는 MemberCategory 목록 조회
    List<MemberCategory> findByMemberId(Long memberId);
}
