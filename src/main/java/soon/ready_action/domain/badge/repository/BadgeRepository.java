package soon.ready_action.domain.badge.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.ready_action.domain.badge.entity.Badge;
import soon.ready_action.domain.badge.entity.BadgeType;
import soon.ready_action.domain.member.entity.Member;

@RequiredArgsConstructor
@Repository
public class BadgeRepository {

    private final BadgeJpaRepository badgejpaRepository;

    public void save(Badge badge) {
        badgejpaRepository.save(badge);
    }

    public List<BadgeType> findByMember(Member member) {
        return badgejpaRepository.findByMember(member);
    }
}
