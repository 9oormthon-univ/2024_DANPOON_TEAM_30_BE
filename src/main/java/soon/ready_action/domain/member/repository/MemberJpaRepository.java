package soon.ready_action.domain.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import soon.ready_action.domain.member.entity.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByKakaoId(String kakaoId);

}
