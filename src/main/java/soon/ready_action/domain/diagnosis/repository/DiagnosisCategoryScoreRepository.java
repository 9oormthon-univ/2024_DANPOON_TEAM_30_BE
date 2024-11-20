package soon.ready_action.domain.diagnosis.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.ready_action.domain.diagnosis.repository.jpa.diagnosisCategoryScoreJpaRepository;

@RequiredArgsConstructor
@Repository
public class DiagnosisCategoryScoreRepository {

    private final diagnosisCategoryScoreJpaRepository jpaRepository;
}
