package soon.ready_action.domain.diagnosis.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import soon.ready_action.domain.diagnosis.entity.DiagnosisQuestion;
import soon.ready_action.domain.diagnosis.entity.DiagnosisResult;
import soon.ready_action.domain.diagnosis.repository.jpa.DiagnosisResultJpaRepository;
import soon.ready_action.domain.member.entity.Member;

@RequiredArgsConstructor
@Repository
public class DiagnosisResultRepository {

    private final DiagnosisResultJpaRepository jpaRepository;

    public void saveAll(List<DiagnosisResult> diagnosisResults) {
        jpaRepository.saveAll(diagnosisResults);
    }

    public List<DiagnosisResult> findDiagnosisResultsByMemberAndCategoryAndAnswerType(
        Long memberId, String categoryTitle
    ) {
        return jpaRepository.findDiagnosisResultsByMemberAndCategoryAndAnswerType(
            memberId, categoryTitle
        );
    }

    public List<DiagnosisResult> findByMemberAndQuestions(List<DiagnosisQuestion> question, Member member) {
        return jpaRepository.findByMemberAndQuestionIn(member, question);
    }
}
