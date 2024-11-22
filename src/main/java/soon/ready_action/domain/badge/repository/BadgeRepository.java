package soon.ready_action.domain.badge.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BadgeRepository {

    private final BadgeJpaRepository badgejpaRepository;
}
