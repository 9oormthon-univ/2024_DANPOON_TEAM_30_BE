package soon.ready_action.domain.diagnosis.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.diagnosis.entity.DiagnosisQuestion;

public interface diagnosisQuestionJpaRepository extends JpaRepository<DiagnosisQuestion, Long> {

    @Query("SELECT dq FROM DiagnosisQuestion dq WHERE dq.isOnboardingQuestion=true and dq.category=:category")
    Optional<DiagnosisQuestion> findByOnboardingQuestionForCategory(Category category);
}
