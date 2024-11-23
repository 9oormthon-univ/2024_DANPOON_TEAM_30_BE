package soon.ready_action.domain.program.service;

import static soon.ready_action.domain.diagnosis.repository.DiagnosisCategoryScoreRepository.LOW_SCORE;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.category.repository.CategoryRepository;
import soon.ready_action.domain.diagnosis.dto.CalculateDiagnosisResult;
import soon.ready_action.domain.program.dto.response.ProgramResponse;
import soon.ready_action.domain.program.dto.response.ProgramResponse.DetailResponse;
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
    private final CategoryRepository categoryRepository;
    private final ScrapService scrapService;

    // 전체 조회
    @Transactional(readOnly = true)
    public ProgramResponse getProgramsByCategory(String categoryTitle, int page) {
        Long memberId = TokenService.getLoginMemberId(); // 로그인된 회원 ID 가져오기

        Pageable pageable = PageRequest.of(page - 1, 5); // 페이지는 1부터 시작하므로, 1을 빼서 0부터 시작하도록 설정

        // 해당 카테고리의 프로그램 조회
        List<Program> programs = programRepository.findByCategoryTitle(categoryTitle, pageable);

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

        String categoryTitleResponse = programs.isEmpty() ? "" : programs.get(0).getCategory().getTitle();

        return new ProgramResponse(programContents, categoryTitleResponse); // boolean 제외하고 반환
    }

    // 상세 조회
    @Transactional(readOnly = true)
    public DetailResponse getProgramById(Long programId) {
        Long memberId = TokenService.getLoginMemberId(); // 로그인된 회원 ID 가져오기

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("Program not found with id: " + programId));

        // 스크랩 여부 확인
        boolean isScraped = scrapService.getScrappedProgramIds(memberId).contains(programId);

        return new DetailResponse(
                program.getId(),
                program.getTitle(),
                program.getDescription(),
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
    public ProgramSearchResponse searchPrograms(String keyword, int page) {
        Long memberId = TokenService.getLoginMemberId(); // JWT에서 로그인된 회원 ID 추출

        // 페이지는 1부터 시작하므로, 1을 빼서 0부터 시작하도록 설정
        Pageable pageable = PageRequest.of(page - 1, 5); // size를 5로 고정
        List<Program> programs = programRepository.findByTitleContaining(keyword, pageable); // 제목으로 프로그램 검색

        // 사용자가 스크랩한 프로그램 ID 조회
        List<Long> scrappedProgramIds = scrapService.getScrappedProgramIds(memberId);

        // 검색 결과 리스트 매핑
        List<ProgramSearchResponse.SearchResult> searchResults = programs.stream()
                .map(program -> new ProgramSearchResponse.SearchResult(
                        program.getId(),
                        program.getTitle(),
                        program.getStartDate(),
                        program.getEndDate(),
                        program.getProgramStatus(),
                        scrappedProgramIds.contains(program.getId()), // 스크랩 여부
                        program.getCategory().getTitle()
                ))
                .collect(Collectors.toList());

        return new ProgramSearchResponse(searchResults);
    }

    public List<DetailResponse> recommendRandomProgram(List<CalculateDiagnosisResult> results) {
        Long loginMemberId = TokenService.getLoginMemberId();

        return results.stream()
            .filter(result -> result.score() <= LOW_SCORE)
            .map(result -> {
                Category category = categoryRepository.findByTitle(result.categoryTitle());
                List<Program> programs = programRepository.findByCategoryTitle(category.getTitle());

                int randomIndex = (int) (Math.random() * programs.size());
                Program program = programs.get(randomIndex);
                boolean isScraped = scrapService.getScrappedProgramIds(loginMemberId).contains(program.getId());

                return DetailResponse.builder()
                        .id(program.getId())
                        .applicationUrl(program.getApplicationUrl())
                        .title(program.getTitle())
                        .categoryTitle(program.getCategory().getTitle())
                        .startDate(program.getStartDate())
                        .endDate(program.getEndDate())
                        .isScraped(isScraped)
                        .status(program.getProgramStatus())
                        .build();
                })
            .toList();
    }

    // 카테고리 리스트에 해당하는 최신 프로그램 3개 조회
    public List<ProgramResponse.DetailResponse> getLatestProgramsByCategories(List<Long> categoryIds, Pageable pageable, Long memberId) {
        // 프로그램 조회 (Pageable을 고려하여 변경)
        List<Program> programs = programRepository.findTop3ByCategoryIdsOrderByStartDateDesc(categoryIds, pageable);

        // 사용자가 스크랩한 프로그램 ID 목록 가져오기
        List<Long> scrappedProgramIds = scrapService.getScrappedProgramIds(memberId);

        // 프로그램 리스트를 DetailResponse로 변환하여 반환
        return programs.stream()
                .map(program -> new ProgramResponse.DetailResponse(
                        program.getId(),
                        program.getTitle(),
                        program.getDescription(),
                        program.getStartDate(),
                        program.getEndDate(),
                        program.getProgramStatus(),
                        program.getApplicationUrl(),
                        program.getCategory().getTitle(),
                        scrappedProgramIds.contains(program.getId()) // 실제 스크랩 여부 체크
                ))
                .collect(Collectors.toList());
    }
}