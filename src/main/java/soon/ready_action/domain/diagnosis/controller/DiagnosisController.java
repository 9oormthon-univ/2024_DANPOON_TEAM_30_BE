package soon.ready_action.domain.diagnosis.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import soon.ready_action.domain.auth.dto.response.AuthResponse;
import soon.ready_action.domain.member.entity.Role;
import soon.ready_action.global.oauth2.dto.CustomOAuth2Member;
import soon.ready_action.global.oauth2.jwt.dto.response.TokenResponse;
import soon.ready_action.global.oauth2.service.TokenService;
import soon.ready_action.global.provider.CustomObjectMapperProvider;

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
