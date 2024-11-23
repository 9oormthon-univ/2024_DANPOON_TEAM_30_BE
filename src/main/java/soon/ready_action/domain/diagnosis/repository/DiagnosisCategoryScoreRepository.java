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

    // 회원의 자가진단 총 점수 계산
    public int getTotalScoreByMemberId(Long memberId) {
        return jpaRepository.findByMemberId(memberId).stream()
                .mapToInt(DiagnosisCategoryScore::getScore)
                .sum();
    }
}
