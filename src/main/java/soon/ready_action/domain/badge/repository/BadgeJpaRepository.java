package soon.ready_action.domain.badge.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import soon.ready_action.domain.badge.entity.Badge;
import soon.ready_action.domain.badge.entity.BadgeType;
import soon.ready_action.domain.member.entity.Member;

public interface BadgeJpaRepository extends JpaRepository<Badge, Long> {

    @Query("SELECT b.type FROM Badge b WHERE b.member = :member")
    List<BadgeType> findBadgeTypesByMemberId(@Param("member") Member member);
}
