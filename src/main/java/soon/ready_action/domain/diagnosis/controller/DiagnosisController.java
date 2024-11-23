package soon.ready_action.domain.diagnosis.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import soon.ready_action.domain.diagnosis.dto.request.CategoryWithDiagnosisRequest;
import soon.ready_action.domain.diagnosis.dto.response.DiagnosisQuestionResponse;
import soon.ready_action.domain.diagnosis.dto.response.DiagnosisResultDTO;
import soon.ready_action.domain.diagnosis.dto.response.DiagnosisResultWrapper;
import soon.ready_action.domain.diagnosis.service.DiagnosisQuestionService;
import soon.ready_action.domain.diagnosis.service.DiagnosisResultService;

@RequiredArgsConstructor
@RequestMapping("/api/v1/diagnosis")
@RestController
public class DiagnosisController extends DiagnosisDocsController {

    private final DiagnosisResultService resultService;
    private final DiagnosisQuestionService questionService;

    @Override
    @GetMapping("/questions")
    public ResponseEntity<List<DiagnosisQuestionResponse>> handleDiagnosisQuestion(
//        @RequestParam Long lastQuestionId,
//        @RequestParam String categoryTitle
        @RequestParam int page
    ) {
//        var wrapper = questionService.getPagedDiagnosisQuestion(lastQuestionId, categoryTitle);
        var wrapper = questionService.getNumberingPagination(page - 1);

        return ResponseEntity.ok(wrapper);
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> handleDiagnosisQuestion(
        @RequestBody CategoryWithDiagnosisRequest request
    ) {
        resultService.saveDiagnosisResults(request);

        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/result")
    public ResponseEntity<DiagnosisResultWrapper> handleDiagnosisResult() {
        DiagnosisResultWrapper wrapper = resultService.getDiagnosisResult();

        return ResponseEntity.ok(wrapper);
    }

    @Operation(summary = "진단 결과 조회", description = "진단 결과 및 뱃지 정보 조회")
    @GetMapping("/diagnosis-page")
    public ResponseEntity<DiagnosisResultDTO> handleDiagnosisResultWithBadges() {
        DiagnosisResultDTO diagnosisResult = resultService.getDiagnosisResultWithBadges();
        return ResponseEntity.ok(diagnosisResult);
    }
}
