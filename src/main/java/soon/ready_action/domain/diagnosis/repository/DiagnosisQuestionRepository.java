package soon.ready_action.domain.diagnosis.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.ready_action.domain.diagnosis.repository.jpa.diagnosisQuestionJpaRepository;

@RequiredArgsConstructor
@Repository
public class DiagnosisQuestionRepository {

    private final diagnosisQuestionJpaRepository jpaRepository;
}
