package soon.ready_action.domain.diagnosis.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.ready_action.domain.diagnosis.entity.DiagnosisCategoryScore;
import soon.ready_action.domain.diagnosis.repository.jpa.DiagnosisCategoryScoreJpaRepository;

@RequiredArgsConstructor
@Repository
public class DiagnosisCategoryScoreRepository {

    private final DiagnosisCategoryScoreJpaRepository jpaRepository;

    public void save(DiagnosisCategoryScore diagnosisCategoryScore) {
        jpaRepository.save(diagnosisCategoryScore);
    }

    public void saveAll(List<DiagnosisCategoryScore> diagnosisCategoryScores) {
        jpaRepository.saveAll(diagnosisCategoryScores);
    }
}
