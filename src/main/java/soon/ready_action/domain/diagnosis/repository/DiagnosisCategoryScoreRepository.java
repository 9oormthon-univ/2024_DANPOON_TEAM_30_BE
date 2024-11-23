package soon.ready_action.domain.diagnosis.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.ready_action.domain.diagnosis.entity.DiagnosisCategoryScore;
import soon.ready_action.domain.diagnosis.repository.jpa.DiagnosisCategoryScoreJpaRepository;

@RequiredArgsConstructor
@Repository
public class DiagnosisCategoryScoreRepository {

    public static final int STANDARD_SCORE = 8;
    public static final int LOW_SCORE = 4;

    private final DiagnosisCategoryScoreJpaRepository jpaRepository;

    public void save(DiagnosisCategoryScore diagnosisCategoryScore) {
        jpaRepository.save(diagnosisCategoryScore);
    }

    public void saveAll(List<DiagnosisCategoryScore> diagnosisCategoryScores) {
        jpaRepository.saveAll(diagnosisCategoryScores);
    }

    public List<DiagnosisCategoryScore> findByMemberId(Long memberId) {
        return jpaRepository.findByMemberId(memberId);
    }
}
