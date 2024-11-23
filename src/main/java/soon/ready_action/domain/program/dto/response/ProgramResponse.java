package soon.ready_action.domain.program.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import soon.ready_action.domain.program.entity.ProgramStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ProgramResponse {

    private List<ProgramContent> contents;
    private String categoryTitle;

    @Data
    @AllArgsConstructor
    public static class ProgramContent {
        private Long id;
        private String title;
        private LocalDate startDate;
        private LocalDate endDate;
        private ProgramStatus status;
        private String applicationUrl;
        private boolean isScraped;
    }

    @Builder
    @Data
    @AllArgsConstructor
    public static class DetailResponse {
        private Long id;
        private String title;
        private String description;
        private LocalDate startDate;
        private LocalDate endDate;
        private ProgramStatus status;
        private String applicationUrl;
        private String categoryTitle;
        private boolean isScraped;
    }
}
