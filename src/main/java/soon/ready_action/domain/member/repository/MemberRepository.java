package soon.ready_action.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soon.ready_action.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
