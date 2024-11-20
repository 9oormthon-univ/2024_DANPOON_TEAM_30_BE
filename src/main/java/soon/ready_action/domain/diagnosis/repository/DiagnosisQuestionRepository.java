package soon.ready_action.domain.diagnosis.repository;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.diagnosis.entity.DiagnosisQuestion;
import soon.ready_action.domain.diagnosis.repository.jpa.diagnosisQuestionJpaRepository;

@RequiredArgsConstructor
@Repository
public class DiagnosisQuestionRepository {

    private final diagnosisQuestionJpaRepository diagnosisQuestionJpaRepository;

    public long count() {
        return diagnosisQuestionJpaRepository.count();
    }

    public void save(DiagnosisQuestion diagnosisQuestion) {
        diagnosisQuestionJpaRepository.save(diagnosisQuestion);
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
}
