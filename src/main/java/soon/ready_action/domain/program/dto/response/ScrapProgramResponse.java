package soon.ready_action.domain.program.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import soon.ready_action.domain.program.entity.ProgramStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ScrapProgramResponse {

    private List<ScrapProgramSearchResult> data;

    @Data
    @AllArgsConstructor
    public static class ScrapProgramSearchResult {
        private Long id;
        private String title;
        private LocalDate startDate;
        private LocalDate endDate;
        private ProgramStatus status;
        private String category;
        private boolean scraped;
    }
}
