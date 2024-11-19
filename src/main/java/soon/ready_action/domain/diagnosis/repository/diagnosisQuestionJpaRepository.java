package soon.ready_action.domain.diagnosis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soon.ready_action.domain.diagnosis.entity.DiagnosisQuestion;

public interface diagnosisQuestionJpaRepository extends JpaRepository<DiagnosisQuestion, Long> {

}
