package soon.ready_action.domain.diagnosis.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import soon.ready_action.domain.diagnosis.entity.DiagnosisResult;

public interface diagnosisResultJpaRepository extends JpaRepository<DiagnosisResult, Long> {

}
