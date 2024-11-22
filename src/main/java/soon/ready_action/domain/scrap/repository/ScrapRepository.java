package soon.ready_action.domain.scrap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soon.ready_action.domain.member.entity.Member;
import soon.ready_action.domain.program.entity.Program;
import soon.ready_action.domain.scrap.entity.Scrap;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    // 회원과 프로그램에 대한 중복 스크랩 체크
    boolean existsByMemberAndProgram(Member member, Program program);

    // 회원과 프로그램으로 스크랩 찾기
    Optional<Scrap> findByMemberAndProgram(Member member, Program program);

    // 회원 ID로 스크랩한 프로그램 목록 조회
    List<Scrap> findByMemberId(Long memberId);
}
