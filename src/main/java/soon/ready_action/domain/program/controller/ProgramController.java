package soon.ready_action.domain.program.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import soon.ready_action.domain.program.dto.response.ProgramResponse;
import soon.ready_action.domain.program.dto.response.ProgramSearchResponse;
import soon.ready_action.domain.program.service.ProgramService;
import soon.ready_action.domain.scrap.service.ScrapService;
import soon.ready_action.global.exception.dto.response.ErrorResponse;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/programs")
public class ProgramController {

    private final ProgramService programService;
    private final ScrapService scrapService;

    // 전체 조회
    @Operation(summary = "프로그램 전체 조회", description = "카테고리별 프로그램 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ProgramResponse.class))),
            @ApiResponse(responseCode = "404", description = "잘못된 경로", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<ProgramResponse> getProgramsByCategory(
            @RequestParam Long categoryId,
            @RequestParam int size,
            @RequestParam(required = false) Long lastProgramId
    ) {
        ProgramResponse response = programService.getProgramsByCategory(categoryId, size, lastProgramId);
        return ResponseEntity.ok(response);
    }

    // 상세 조회
    @Operation(summary = "프로그램 상세 조회", description = "하나의 프로그램 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ProgramResponse.DetailResponse.class))),
            @ApiResponse(responseCode = "404", description = "잘못된 경로", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{programId}")
    public ResponseEntity<ProgramResponse.DetailResponse> getProgramById(@PathVariable Long programId) {
        ProgramResponse.DetailResponse detailResponse = programService.getProgramById(programId);
        return ResponseEntity.ok(detailResponse);
    }

    // 검색
    @Operation(summary = "프로그램 검색", description = "검색어가 제목에 포함된 프로그램 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "검색 성공", content = @Content(schema = @Schema(implementation = ProgramSearchResponse.class))),
            @ApiResponse(responseCode = "404", description = "잘못된 경로", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<Object> searchPrograms(
            @RequestParam String keyword,
            @RequestParam int size,
            @RequestParam(required = false) Long lastProgramId
    ) {
        ProgramSearchResponse response = programService.searchPrograms(keyword, size, lastProgramId);

        if (response.getData().isEmpty()) {
            return ResponseEntity.ok(
                    Map.of(
                            "message", "일치하는 프로그램이 없습니다."
                    )
            );
        }

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "프로그램 스크랩", description = "프로그램을 스크랩")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 성공"),
            @ApiResponse(responseCode = "404", description = "잘못된 경로", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{programId}/scrap")
    public ResponseEntity<String> scrapProgram(@PathVariable Long programId) {
        try {
            scrapService.scrapProgram(programId);
            return ResponseEntity.ok("프로그램이 성공적으로 스크랩되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "프로그램 스크랩 취소", description = "프로그램 스크랩을 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 취소 성공"),
            @ApiResponse(responseCode = "404", description = "잘못된 경로", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{programId}/scrap")
    public ResponseEntity<String> cancelScrap(@PathVariable Long programId) {
        try {
            scrapService.cancelScrap(programId);
            return ResponseEntity.ok("프로그램 스크랩이 성공적으로 취소되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
