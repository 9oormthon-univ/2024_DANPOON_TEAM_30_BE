package soon.ready_action.domain.diagnosis.repository.jpa;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import soon.ready_action.domain.diagnosis.entity.DiagnosisCategoryScore;
import soon.ready_action.domain.diagnosis.repository.DiagnosisCategoryScoreRepository;

public interface DiagnosisCategoryScoreJpaRepository extends
    JpaRepository<DiagnosisCategoryScore, Long> {

    List<DiagnosisCategoryScore> findByMemberId(Long memberId);
}
