package soon.ready_action.domain.diagnosis.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.ready_action.domain.diagnosis.repository.jpa.diagnosisResultJpaRepository;

@RequiredArgsConstructor
@Repository
public class DiagnosisResultRepository {

    private final diagnosisResultJpaRepository jpaRepository;
}
