package soon.ready_action.domain.diagnosis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import soon.ready_action.domain.diagnosis.dto.request.CategoryWithDiagnosisRequest;
import soon.ready_action.domain.diagnosis.dto.request.DiagnosisQuestionPaginationRequest;
import soon.ready_action.domain.diagnosis.dto.response.DiagnosisQuestionPaginationResponseWrapper;
import soon.ready_action.global.exception.dto.response.ErrorResponse;

@Tag(name = "Diagnosis Controller", description = "Diagnosis API")
public abstract class DiagnosisDocsController {

    @Operation(summary = "온보딩 질문 제출", description = "선택한 질문의 응답 제출")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "void"),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public abstract ResponseEntity<Void> handleOnboardingDiagnosis(
        CategoryWithDiagnosisRequest request
    );

    @Operation(summary = "질문 페이징 조회", description = "질문 페이징 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "DiagnosisQuestionPaginationResponseWrapper", content = @Content(schema = @Schema(implementation = DiagnosisQuestionPaginationResponseWrapper.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public abstract ResponseEntity<DiagnosisQuestionPaginationResponseWrapper> handleDiagnosisQuestion(
        DiagnosisQuestionPaginationRequest request
    );
}
