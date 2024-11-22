package soon.ready_action.domain.program.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import soon.ready_action.domain.program.entity.ProgramStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ProgramSearchResponse {

    private List<SearchResult> data; // 검색 결과 목록
    private int totalElements;      // 총 검색된 개수
    private boolean hasNextPage;    // 다음 페이지 여부

    @Data
    @AllArgsConstructor
    public static class SearchResult {
        private Long id;
        private String title;
        private LocalDate startDate;
        private LocalDate endDate;
        private ProgramStatus status;
        private boolean isScraped;  // 스크랩 여부
        private String category;   // 카테고리 제목
    }
}

