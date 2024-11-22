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
import soon.ready_action.domain.diagnosis.service.DiagnosisService;

@RequiredArgsConstructor
@RequestMapping("/api/v1/diagnosis")
@RestController
public class DiagnosisController extends DiagnosisDocsController {

    private final DiagnosisService diagnosisService;

    @Override
    @PostMapping
    public ResponseEntity<Void> handleOnboardingDiagnosis(
        @RequestBody CategoryWithDiagnosisRequest request
    ) {
        diagnosisService.saveOnboardingDiagnosisResults(request);

        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/questions")
    public ResponseEntity<DiagnosisQuestionPaginationResponseWrapper> handleDiagnosisQuestion(
        @RequestBody DiagnosisQuestionPaginationRequest request
    ) {
        var wrapper = diagnosisService.getPagedDiagnosisQuestion(request);

        return ResponseEntity.ok(wrapper);
    }
}
