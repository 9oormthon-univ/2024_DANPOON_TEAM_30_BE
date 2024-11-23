package soon.ready_action.domain.knowledge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soon.ready_action.domain.knowledge.dto.KnowledgeResponse;
import soon.ready_action.domain.knowledge.dto.KnowledgeResponse.DetailResponse;
import soon.ready_action.domain.knowledge.service.KnowledgeService;
import soon.ready_action.global.exception.dto.response.ErrorResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/knowledge")
@Tag(name = "Knowledge Controller", description = "Knowledge API")
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    // 전체 조회
    @Operation(summary = "자립지식 전체 조회", description = "카테고리별 자립지식 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = KnowledgeResponse.class))),
            @ApiResponse(responseCode = "404", description = "잘못된 경로", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<KnowledgeResponse> getKnowledgeByCategory(
            @RequestParam String categoryTitle,
            @RequestParam int page
    ) {
        KnowledgeResponse response = knowledgeService.getKnowledgeByCategory(categoryTitle, page);
        return ResponseEntity.ok(response);
    }

    // 상세 조회
    @GetMapping("/{knowledgeId}")
    @Operation(summary = "자립지식 상세 조회", description = "하나의 자립지식 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = KnowledgeResponse.class))),
            @ApiResponse(responseCode = "404", description = "잘못된 경로", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<DetailResponse> getKnowledgeById(@PathVariable Long knowledgeId) {
        DetailResponse detailResponse = knowledgeService.getKnowledgeById(knowledgeId);
        return ResponseEntity.ok(detailResponse);
    }
}
