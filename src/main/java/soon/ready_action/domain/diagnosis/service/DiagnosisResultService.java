package soon.ready_action.domain.diagnosis.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.badge.service.BadgeService;
import soon.ready_action.domain.diagnosis.dto.request.CategoryWithDiagnosisRequest;
import soon.ready_action.domain.diagnosis.entity.AnswerType;
import soon.ready_action.domain.diagnosis.entity.DiagnosisQuestion;
import soon.ready_action.domain.diagnosis.entity.DiagnosisResult;
import soon.ready_action.domain.diagnosis.repository.DiagnosisQuestionRepository;
import soon.ready_action.domain.diagnosis.repository.DiagnosisResultRepository;
import soon.ready_action.domain.member.entity.Member;
import soon.ready_action.domain.member.repository.MemberRepository;
import soon.ready_action.global.oauth2.service.TokenService;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiagnosisResultService {

    private final DiagnosisResultRepository resultRepository;
    private final DiagnosisQuestionRepository questionRepository;
    private final DiagnosisScoreService scoreService;
    private final MemberRepository memberRepository;
    private final BadgeService badgeService;

    @Transactional
    public void saveDiagnosisResults(CategoryWithDiagnosisRequest request) {
        Member loginMember = memberRepository.findById(TokenService.getLoginMemberId());
        Map<Long, Boolean> questionResult = request.questionResult();
        List<DiagnosisQuestion> questions = questionRepository.findAllById(questionResult.keySet());

        List<DiagnosisResult> results = processDiagnosisResults(
            questions, loginMember, questionResult
        );

        resultRepository.saveAll(results);
        scoreService.calculateAndSaveDiagnosisScores(loginMember.getId());
        badgeService.awardBadgesForStandardScores(loginMember);
    }

    private List<DiagnosisResult> processDiagnosisResults(
        List<DiagnosisQuestion> questions,
        Member loginMember, Map<Long, Boolean> questionResult
    ) {
        Map<Long, DiagnosisResult> existingResults = resultRepository
            .findByMemberAndQuestions(questions, loginMember)
            .stream()
            .collect(Collectors.toMap(result -> result.getQuestion().getId(), result -> result));

        return questions.stream()
            .map(question -> updateOrCreateDiagnosisResult(
                question, loginMember, questionResult, existingResults
            )).toList();
    }

    private DiagnosisResult updateOrCreateDiagnosisResult(
        DiagnosisQuestion question,
        Member loginMember, Map<Long, Boolean> questionResult,
        Map<Long, DiagnosisResult> existingResults
    ) {
        DiagnosisResult result = existingResults.get(question.getId());
        Boolean answer = questionResult.get(question.getId());

        if (result != null) {
            result.updateAnswerType(AnswerType.from(answer));
            return result;
        } else {
            return createDiagnosisResult(question, loginMember, answer);
        }
    }

    private DiagnosisResult createDiagnosisResult(
        DiagnosisQuestion question,
        Member member,
        Boolean answer
    ) {
        return DiagnosisResult.builder()
            .question(question)
            .member(member)
            .answerType(AnswerType.from(answer))
            .build();
    }
}