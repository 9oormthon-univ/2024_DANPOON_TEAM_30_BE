package soon.ready_action.domain.program.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import soon.ready_action.domain.program.dto.ProgramResponse;
import soon.ready_action.domain.program.service.ProgramService;
import soon.ready_action.global.exception.dto.response.ErrorResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/programs")
public class ProgramController {

    private final ProgramService programService;

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
}
