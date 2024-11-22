package soon.ready_action.domain.badge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soon.ready_action.domain.badge.entity.Badge;

public interface BadgeJpaRepository extends JpaRepository<Badge, Long> {

}
