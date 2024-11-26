package soon.ready_action.domain.diagnosis.repository.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import soon.ready_action.domain.diagnosis.entity.DiagnosisCategoryScore;

public interface DiagnosisCategoryScoreJpaRepository extends
    JpaRepository<DiagnosisCategoryScore, Long> {

    List<DiagnosisCategoryScore> findByMemberId(Long memberId);
}
