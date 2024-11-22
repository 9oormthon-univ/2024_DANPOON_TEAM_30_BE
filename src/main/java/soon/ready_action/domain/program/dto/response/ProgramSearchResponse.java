package soon.ready_action.domain.program.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import soon.ready_action.domain.program.entity.ProgramStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ProgramSearchResponse {

    private List<SearchResult> data;
    private int totalElements;
    private boolean hasNextPage;

    @Data
    @AllArgsConstructor
    public static class SearchResult {
        private Long id;
        private String title;
        private LocalDate startDate;
        private LocalDate endDate;
        private ProgramStatus status;
        private boolean isScraped;
        private String category;
    }
}

