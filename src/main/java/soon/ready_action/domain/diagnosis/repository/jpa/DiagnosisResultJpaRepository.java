package soon.ready_action.domain.diagnosis.repository.jpa;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import soon.ready_action.domain.diagnosis.entity.DiagnosisQuestion;
import soon.ready_action.domain.diagnosis.entity.DiagnosisResult;
import soon.ready_action.domain.diagnosis.repository.DiagnosisRepositoryCustom;
import soon.ready_action.domain.member.entity.Member;

public interface DiagnosisResultJpaRepository extends
    JpaRepository<DiagnosisResult, Long>,
    DiagnosisRepositoryCustom {

    List<DiagnosisResult> findByMemberAndQuestionIn(Member member, List<DiagnosisQuestion> questions);
}
