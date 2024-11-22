package soon.ready_action.domain.diagnosis.repository;

import static soon.ready_action.domain.category.entity.QCategory.category;
import static soon.ready_action.domain.diagnosis.entity.QDiagnosisQuestion.diagnosisQuestion;
import static soon.ready_action.domain.diagnosis.entity.QDiagnosisResult.diagnosisResult;
import static soon.ready_action.domain.member.entity.QMember.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import soon.ready_action.domain.diagnosis.entity.AnswerType;
import soon.ready_action.domain.diagnosis.entity.DiagnosisResult;

@RequiredArgsConstructor
public class DiagnosisRepositoryCustomImpl implements DiagnosisRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DiagnosisResult> findDiagnosisResultsByMemberAndCategoryAndAnswerType(
        Long memberId, String categoryTitle
    ) {
        return queryFactory
            .selectFrom(diagnosisResult)
            .join(diagnosisResult.member, member).fetchJoin()
            .join(diagnosisResult.question, diagnosisQuestion).fetchJoin()
            .join(diagnosisQuestion.category, category).fetchJoin()
            .where(
                member.id.eq(memberId),
                category.title.eq(categoryTitle),
                diagnosisResult.answerType.eq(AnswerType.O_SELECTED)
            )
            .fetch();
    }
}
