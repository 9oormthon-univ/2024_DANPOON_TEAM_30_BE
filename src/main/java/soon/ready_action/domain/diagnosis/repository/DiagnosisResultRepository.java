package soon.ready_action.domain.diagnosis.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.ready_action.domain.diagnosis.entity.DiagnosisResult;
import soon.ready_action.domain.diagnosis.repository.jpa.diagnosisResultJpaRepository;

@RequiredArgsConstructor
@Repository
public class DiagnosisResultRepository {

    private final diagnosisResultJpaRepository jpaRepository;

    public void saveAll(List<DiagnosisResult> diagnosisResults) {
        jpaRepository.saveAll(diagnosisResults);
    }
}
