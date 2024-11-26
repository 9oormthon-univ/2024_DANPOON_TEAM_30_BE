package soon.ready_action.domain.diagnosis.repository;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.diagnosis.dto.response.DiagnosisQuestionResponse;
import soon.ready_action.domain.diagnosis.entity.DiagnosisQuestion;
import soon.ready_action.domain.diagnosis.repository.jpa.DiagnosisQuestionJpaRepository;
import soon.ready_action.global.exception.DiagnosisQuestionNotFoundException;

@RequiredArgsConstructor
@Repository
public class DiagnosisQuestionRepository {

    private final DiagnosisQuestionJpaRepository diagnosisQuestionJpaRepository;

    public long count() {
        return diagnosisQuestionJpaRepository.count();
    }

    @Transactional
    public void onboardingQuestionSave(String content, Category category) {
        DiagnosisQuestion onboardQuestion = DiagnosisQuestion.builder()
            .content(content)
            .isOnboardingRelation(false)
            .isOnboardingQuestion(true)
            .category(category)
            .build();

        diagnosisQuestionJpaRepository.save(onboardQuestion);
    }

    @Transactional
    public void questionsSave(Map<String, Boolean> questions, Category category) {
        for (String content : questions.keySet()) {
            DiagnosisQuestion question = DiagnosisQuestion.builder()
                .content(content)
                .isOnboardingRelation(questions.get(content))
                .isOnboardingQuestion(false)
                .category(category)
                .build();

            diagnosisQuestionJpaRepository.save(question);
        }
    }

    public DiagnosisQuestion findByOnboardingQuestionForCategory(Category category) {
        return diagnosisQuestionJpaRepository.findByOnboardingQuestionForCategory(category)
            .orElseThrow(DiagnosisQuestionNotFoundException::new);
    }

    public List<DiagnosisQuestion> findAllById(List<Long> ids) {
        return diagnosisQuestionJpaRepository.findAllById(ids);
    }

    public List<DiagnosisQuestionResponse> getPagedDiagnosisQuestion(
        Long lastQuestionId, Long memberId, String categoryTitle
    ) {
        return diagnosisQuestionJpaRepository.getPagedDiagnosisQuestion(
            lastQuestionId, memberId, categoryTitle
        );
    }

    public boolean determineHasNextPage(
        List<DiagnosisQuestionResponse> paginatedDiagnosisQuestion
    ) {
        return diagnosisQuestionJpaRepository.determineHasNextPage(paginatedDiagnosisQuestion);
    }
}
