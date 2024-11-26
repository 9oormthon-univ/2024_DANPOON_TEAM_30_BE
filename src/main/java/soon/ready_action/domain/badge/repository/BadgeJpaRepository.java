package soon.ready_action.domain.badge.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import soon.ready_action.domain.badge.entity.Badge;
import soon.ready_action.domain.badge.entity.BadgeType;
import soon.ready_action.domain.member.entity.Member;

public interface BadgeJpaRepository extends JpaRepository<Badge, Long> {

    List<BadgeType> findByMember(Member member);
}
