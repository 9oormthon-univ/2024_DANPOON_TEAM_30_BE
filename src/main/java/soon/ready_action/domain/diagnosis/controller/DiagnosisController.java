package soon.ready_action.domain.diagnosis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soon.ready_action.domain.diagnosis.dto.request.CategoryWithDiagnosisRequest;
import soon.ready_action.domain.diagnosis.dto.request.DiagnosisQuestionPaginationRequest;
import soon.ready_action.domain.diagnosis.dto.response.DiagnosisQuestionPaginationResponseWrapper;
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
    public ResponseEntity<DiagnosisQuestionPaginationResponseWrapper> handleDiagnosisQuestion(
        @RequestBody DiagnosisQuestionPaginationRequest request
    ) {
        var wrapper = questionService.getPagedDiagnosisQuestion(request);

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
    public ResponseEntity<?> handleDiagnosisResult() {
        resultService.getDiagnosisResult();

        return null;
    }
}
