package soon.ready_action.domain.diagnosis.repository.pagination;

import static soon.ready_action.domain.category.entity.QCategory.category;
import static soon.ready_action.domain.diagnosis.entity.QDiagnosisQuestion.diagnosisQuestion;
import static soon.ready_action.domain.diagnosis.entity.QDiagnosisResult.diagnosisResult;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.List;
import lombok.RequiredArgsConstructor;
import soon.ready_action.domain.diagnosis.dto.response.DiagnosisQuestionResponse;

@RequiredArgsConstructor
public class DiagnosisQuestionPaginationRepositoryImpl implements
    DiagnosisQuestionPaginationRepository {

    private final JPAQueryFactory queryFactory;
    private static final int PAGE_SIZE = 5;

    public List<DiagnosisQuestionResponse> getPagedDiagnosisQuestion(
        Long lastQuestionId,
        Long memberId,
        String categoryTitle
    ) {
        return queryFactory.select(
                Projections.constructor(DiagnosisQuestionResponse.class,
                    diagnosisQuestion.id.as("questionId"),
                    category.title.as("category"),
                    Expressions.stringTemplate("function('str', {0})", diagnosisQuestion.content)
                        .as("question"),
                    Expressions.stringTemplate("function('str', {0})", diagnosisResult.answerType)
                        .as("answerType")
                )
            )
            .from(diagnosisQuestion)
            .leftJoin(diagnosisQuestion.category, category)
            .leftJoin(diagnosisResult)
            .on(diagnosisResult.question.eq(diagnosisQuestion)
                .and(diagnosisResult.member.id.eq(memberId)))
            .where(
                categoryCondition(categoryTitle),
                paginationCondition(lastQuestionId),
                isOnboardingCondition()
            )
            .orderBy(diagnosisQuestion.category.id.asc(), diagnosisQuestion.id.asc())
            .limit(PAGE_SIZE + 1)
            .fetch();
    }

    private BooleanExpression categoryCondition(String categoryTitle) {
        return categoryTitle != null ? category.title.eq(categoryTitle) : null;
    }

    private BooleanExpression paginationCondition(Long lastQuestionId) {
        return lastQuestionId != null ? diagnosisQuestion.id.gt(lastQuestionId) : null;
    }

    private BooleanExpression isOnboardingCondition() {
        return diagnosisQuestion.isOnboardingQuestion.isFalse();
    }
}