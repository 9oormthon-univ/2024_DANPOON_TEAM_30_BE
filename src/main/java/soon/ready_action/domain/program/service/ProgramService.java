package soon.ready_action.domain.program.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import soon.ready_action.domain.program.dto.response.ProgramResponse;
import soon.ready_action.domain.program.dto.response.ProgramSearchResponse;
import soon.ready_action.domain.program.entity.Program;
import soon.ready_action.domain.program.repository.ProgramRepository;
import soon.ready_action.domain.scrap.service.ScrapService;
import soon.ready_action.global.oauth2.service.TokenService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;
    private final ScrapService scrapService;

    // 전체 조회
    @Transactional(readOnly = true)
    public ProgramResponse getProgramsByCategory(Long categoryId, int size, Long lastProgramId) {
        List<Program> programs;
        Long memberId = TokenService.getLoginMemberId(); // 로그인된 회원 ID 가져오기

        Pageable pageable = PageRequest.of(0, size);
        if (lastProgramId == null) {
            programs = programRepository.findFirstPrograms(categoryId, pageable);
        } else {
            programs = programRepository.findProgramsAfterId(categoryId, lastProgramId, pageable);
        }

        // 사용자가 스크랩한 프로그램 ID 조회
        List<Long> scrappedProgramIds = scrapService.getScrappedProgramIds(memberId);

        // 결과 매핑
        List<ProgramResponse.ProgramContent> programContents = programs.stream()
                .map(program -> new ProgramResponse.ProgramContent(
                        program.getId(),
                        program.getTitle(),
                        program.getStartDate(),
                        program.getEndDate(),
                        program.getProgramStatus(),
                        program.getApplicationUrl(),
                        scrappedProgramIds.contains(program.getId()) // 스크랩 여부
                ))
                .collect(Collectors.toList());

        String categoryTitle = programs.isEmpty() ? "" : programs.get(0).getCategory().getTitle();
        boolean hasNextPage = programs.size() == size;

        return new ProgramResponse(programContents, categoryTitle, hasNextPage);
    }

    // 상세 조회
    @Transactional(readOnly = true)
    public ProgramResponse.DetailResponse getProgramById(Long programId) {
        Long memberId = TokenService.getLoginMemberId(); // 로그인된 회원 ID 가져오기

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("Program not found with id: " + programId));

        // 스크랩 여부 확인
        boolean isScraped = scrapService.getScrappedProgramIds(memberId).contains(programId);

        return new ProgramResponse.DetailResponse(
                program.getId(),
                program.getTitle(),
                program.getStartDate(),
                program.getEndDate(),
                program.getProgramStatus(),
                program.getApplicationUrl(),
                program.getCategory().getTitle(),
                isScraped // 스크랩 여부
        );
    }

    // 검색
    @Transactional(readOnly = true)
    public ProgramSearchResponse searchPrograms(String keyword, int size, Long lastProgramId) {
        Long memberId = TokenService.getLoginMemberId(); // JWT에서 로그인된 회원 ID 추출

        Pageable pageable = PageRequest.of(0, size);
        List<Program> programs = programRepository.searchProgramsByTitleWithLastProgramId(keyword, lastProgramId, pageable);

        // 사용자가 스크랩한 프로그램 ID 조회
        List<Long> scrappedProgramIds = scrapService.getScrappedProgramIds(memberId);

        List<ProgramSearchResponse.SearchResult> searchResults = programs.stream()
                .map(program -> new ProgramSearchResponse.SearchResult(
                        program.getId(),
                        program.getTitle(),
                        program.getStartDate(),
                        program.getEndDate(),
                        program.getProgramStatus(),
                        scrappedProgramIds.contains(program.getId()),
                        program.getCategory().getTitle()
                ))
                .collect(Collectors.toList());

        boolean hasNextPage = programs.size() == size;

        return new ProgramSearchResponse(searchResults, programRepository.countProgramsByTitle(keyword), hasNextPage);
    }
}
