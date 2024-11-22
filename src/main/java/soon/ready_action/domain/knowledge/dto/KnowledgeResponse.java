package soon.ready_action.domain.knowledge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class KnowledgeResponse {

    private String categoryTitle;
    private List<KnowledgeContent> contents;
    private int totalElements;
    private Long nextCursor;

    @Data
    @AllArgsConstructor
    public static class KnowledgeContent {
        private Long id;
        private String title;
        private String content;
    }

    @Data
    @AllArgsConstructor
    public static class DetailResponse {
        private Long id;
        private String title;
        private String content;
        private String categoryTitle;
    }
}
