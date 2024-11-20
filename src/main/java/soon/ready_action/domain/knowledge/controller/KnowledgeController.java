package soon.ready_action.domain.knowledge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soon.ready_action.domain.knowledge.dto.KnowledgeResponse;
import soon.ready_action.domain.knowledge.dto.KnowledgeResponse.DetailResponse;
import soon.ready_action.domain.knowledge.service.KnowledgeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/knowledge")
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    // 무한 스크롤 조회
    @GetMapping
    public ResponseEntity<KnowledgeResponse> getKnowledgeByCategory(
            @RequestParam Long categoryId,
            @RequestParam int size,
            @RequestParam(required = false) Long lastKnowledgeId
    ) {
        KnowledgeResponse response = knowledgeService.getKnowledgeByCategory(categoryId, size, lastKnowledgeId);
        return ResponseEntity.ok(response);
    }

    // 상세 조회
    @GetMapping("/{knowledgeId}")
    public ResponseEntity<DetailResponse> getKnowledgeById(@PathVariable Long knowledgeId) {
        DetailResponse detailResponse = knowledgeService.getKnowledgeById(knowledgeId);
        return ResponseEntity.ok(detailResponse);
    }
}
