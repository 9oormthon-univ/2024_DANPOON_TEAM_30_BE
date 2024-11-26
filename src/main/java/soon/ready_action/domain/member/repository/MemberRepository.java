package soon.ready_action.domain.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.ready_action.domain.member.entity.Member;
import soon.ready_action.global.exception.MemberNotFoundException;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public Member findById(Long memberId) {
        return memberJpaRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
    }

    public Member findByKakaoId(String kakaoId) {
        return memberJpaRepository.findByKakaoId(kakaoId)
            .orElse(null);
    }

    public void save(Member member) {
        memberJpaRepository.save(member);
    }
}
