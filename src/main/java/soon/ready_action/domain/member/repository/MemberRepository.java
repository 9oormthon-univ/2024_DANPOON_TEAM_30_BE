package soon.ready_action.domain.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.ready_action.domain.member.entity.Member;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public Member findById(Long memberId) {
        // TODO 예외처리 수정
        return memberJpaRepository.findById(memberId)
            .orElseThrow(RuntimeException::new);
    }
}
