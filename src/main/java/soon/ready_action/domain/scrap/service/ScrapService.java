package soon.ready_action.domain.scrap.service;

import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soon.ready_action.domain.member.entity.Member;
import soon.ready_action.domain.member.repository.MemberRepository;
import soon.ready_action.domain.program.dto.response.ScrapProgramResponse;
import soon.ready_action.domain.program.entity.Program;
import soon.ready_action.domain.program.repository.ProgramRepository;
import soon.ready_action.domain.scrap.entity.Scrap;
import soon.ready_action.domain.scrap.repository.ScrapRepository;
import soon.ready_action.global.oauth2.service.TokenService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final ProgramRepository programRepository;
    private final MemberRepository memberRepository;

    // 스크랩 추가
    @Transactional
    public void scrapProgram(Long programId) {
        // 로그인된 회원 ID 추출
        Long memberId = TokenService.getLoginMemberId();

        // Member와 Program을 검증
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new IllegalArgumentException("Member not found with id: " + memberId);
        }

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("Program not found with id: " + programId));

        // 이미 스크랩 되어 있는지 확인
        if (scrapRepository.existsByMemberAndProgram(member, program)) {
            throw new IllegalArgumentException("이미 스크랩 한 프로그램입니다.");
        }

        // 스크랩 생성 및 저장
        Scrap scrap = new Scrap(program, member);
        scrapRepository.save(scrap);
    }

    // 스크랩 삭제
    @Transactional
    public void cancelScrap(Long programId) {
        // 로그인된 회원 ID 추출
        Long memberId = TokenService.getLoginMemberId();

        // Member와 Program을 검증
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new IllegalArgumentException("Member not found with id: " + memberId);
        }

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("Program not found with id: " + programId));

        // 해당 스크랩을 조회
        Scrap scrap = scrapRepository.findByMemberAndProgram(member, program)
                .orElseThrow(() -> new IllegalArgumentException("Scrap not found for this member and program"));

        // 스크랩 삭제
        scrapRepository.delete(scrap);
    }

    public List<Long> getScrappedProgramIds(Long memberId) {
        return scrapRepository.findByMemberId(memberId).stream()
                .map(scrap -> scrap.getProgram().getId())
                .collect(Collectors.toList());
    }

    // 스크랩한 프로그램 조회
    @Transactional(readOnly = true)
    public ScrapProgramResponse getScrappedPrograms(int page) {
        Long memberId = TokenService.getLoginMemberId();

        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "program.id"));

        // 페이지 처리하여 스크랩된 프로그램 조회
        Page<Scrap> scrapPage = scrapRepository.findByMemberId(memberId, pageable);

        // 스크랩된 프로그램 데이터를 리스트로 변환
        List<ScrapProgramResponse.ScrapProgramSearchResult> result = scrapPage.getContent().stream()
                .map(scrap -> new ScrapProgramResponse.ScrapProgramSearchResult(
                        scrap.getProgram().getId(),
                        scrap.getProgram().getTitle(),
                        scrap.getProgram().getStartDate(),
                        scrap.getProgram().getEndDate(),
                        scrap.getProgram().getProgramStatus(),
                        scrap.getProgram().getCategory().getTitle(),
                        true // 스크랩 여부는 항상 true
                ))
                .collect(Collectors.toList());

        // 전체 페이지가 아닌, 해당 페이지의 데이터만 반환
        return new ScrapProgramResponse(result);
    }
}
