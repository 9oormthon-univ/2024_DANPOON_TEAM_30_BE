package soon.ready_action.domain.diagnosis.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import soon.ready_action.domain.diagnosis.entity.DiagnosisResult;
import soon.ready_action.domain.diagnosis.repository.DiagnosisRepositoryCustom;

public interface DiagnosisResultJpaRepository extends
    JpaRepository<DiagnosisResult, Long>,
    DiagnosisRepositoryCustom {

}
