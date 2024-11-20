package soon.ready_action.domain.program.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import soon.ready_action.domain.program.dto.ProgramResponse;
import soon.ready_action.domain.program.dto.ProgramSearchResponse;
import soon.ready_action.domain.program.entity.Program;
import soon.ready_action.domain.program.repository.ProgramRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;

    // 전체 조회
    @Transactional(readOnly = true)
    public ProgramResponse getProgramsByCategory(Long categoryId, int size, Long lastProgramId) {
        List<Program> programs;

        Pageable pageable = PageRequest.of(0, size);

        if (lastProgramId == null) {
            // 첫 페이지인 경우
            programs = programRepository.findFirstPrograms(categoryId, pageable);
        } else {
            // 이후 페이지인 경우
            programs = programRepository.findProgramsAfterId(categoryId, lastProgramId, pageable);
        }

        // 결과 매핑
        List<ProgramResponse.ProgramContent> programContents = programs.stream()
                .map(program -> new ProgramResponse.ProgramContent(
                        program.getId(),
                        program.getTitle(),
                        program.getStartDate(), // 모집일
                        program.getEndDate(),   // 마감일
                        program.getProgramStatus(), // 계산된 모집 상태
                        program.getApplicationUrl() // 지원 URL
                ))
                .collect(Collectors.toList());

        // 카테고리 타이틀
        String categoryTitle = programs.isEmpty() ? "" : programs.get(0).getCategory().getTitle();

        // 다음 페이지 여부 (프로그램 목록이 size보다 적으면 다음 페이지 없음)
        boolean hasNextPage = programs.size() == size;

        return new ProgramResponse(programContents, categoryTitle, hasNextPage);
    }

    // 상세 조회
    @Transactional(readOnly = true)
    public ProgramResponse.DetailResponse getProgramById(Long programId) {
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("Program not found with id: " + programId));

        return new ProgramResponse.DetailResponse(
                program.getId(),
                program.getTitle(),
                program.getStartDate(),
                program.getEndDate(),
                program.getProgramStatus(),
                program.getApplicationUrl(),
                program.getCategory().getTitle()
        );
    }

    // 검색
    @Transactional(readOnly = true)
    public ProgramSearchResponse searchPrograms(String keyword, int size, Long lastProgramId) {
        Pageable pageable = PageRequest.of(0, size);
        List<Program> programs;

        if (lastProgramId == null) {
            // 첫 페이지인 경우
            programs = programRepository.searchProgramsByTitle(keyword, pageable);
        } else {
            // 이후 페이지인 경우
            programs = programRepository.searchProgramsByTitle(keyword, pageable).stream()
                    .filter(program -> program.getId() < lastProgramId)
                    .collect(Collectors.toList());
        }

        int totalElements = programRepository.countProgramsByTitle(keyword);

        if (programs.isEmpty()) {
            // 결과가 없는 경우
            return new ProgramSearchResponse(List.of(), 0, false);
        }

        List<ProgramSearchResponse.SearchResult> searchResults = programs.stream()
                .map(program -> new ProgramSearchResponse.SearchResult(
                        program.getId(),
                        program.getTitle(),
                        program.getStartDate(),
                        program.getEndDate(),
                        program.getProgramStatus(),
                        false, // 스크랩 여부: 실제 구현 필요
                        program.getCategory().getTitle()
                ))
                .collect(Collectors.toList());

        boolean hasNextPage = programs.size() == size;

        return new ProgramSearchResponse(searchResults, totalElements, hasNextPage);
    }

}
