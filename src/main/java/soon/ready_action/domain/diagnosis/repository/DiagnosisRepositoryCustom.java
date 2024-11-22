package soon.ready_action.domain.diagnosis.repository;

import java.util.List;
import soon.ready_action.domain.diagnosis.entity.DiagnosisResult;

public interface DiagnosisRepositoryCustom {

    List<DiagnosisResult> findDiagnosisResultsByMemberAndCategoryAndAnswerType(
        Long memberId,
        String categoryTitle
    );
}
