package soon.ready_action.domain.diagnosis.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.badge.entity.BadgeType;
import soon.ready_action.domain.badge.service.BadgeService;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.category.repository.CategoryRepository;
import soon.ready_action.domain.diagnosis.dto.CalculateDiagnosisResult;
import soon.ready_action.domain.diagnosis.dto.CategoryWithDiagnosis;
import soon.ready_action.domain.diagnosis.dto.request.CategoryWithDiagnosisRequest;
import soon.ready_action.domain.diagnosis.dto.response.DiagnosisResultDTO;
import soon.ready_action.domain.diagnosis.dto.response.DiagnosisResultWrapper;
import soon.ready_action.domain.diagnosis.entity.AnswerType;
import soon.ready_action.domain.diagnosis.entity.DiagnosisQuestion;
import soon.ready_action.domain.diagnosis.entity.DiagnosisResult;
import soon.ready_action.domain.diagnosis.repository.DiagnosisQuestionRepository;
import soon.ready_action.domain.diagnosis.repository.DiagnosisResultRepository;
import soon.ready_action.domain.member.entity.Member;
import soon.ready_action.domain.member.repository.MemberRepository;
import soon.ready_action.domain.program.dto.response.ProgramResponse.DetailResponse;
import soon.ready_action.domain.program.service.ProgramService;
import soon.ready_action.global.oauth2.service.TokenService;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiagnosisResultService {

    private final DiagnosisResultRepository resultRepository;
    private final DiagnosisQuestionRepository questionRepository;
    private final DiagnosisScoreService scoreService;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final BadgeService badgeService;
    private final ProgramService programService;

    @Transactional
    public void saveDiagnosisResults(CategoryWithDiagnosisRequest request) {
        Member loginMember = getLoginMember();
        List<Long> questionIds = CategoryWithDiagnosisRequest.toQuestionIds(request);
        List<DiagnosisQuestion> questions = questionRepository.findAllById(questionIds);

        List<DiagnosisResult> results = processDiagnosisResults(
            questions, loginMember, request.categoryWithDiagnosis()
        );
        resultRepository.saveAll(results);

        processMemberProgress(loginMember);
    }

    private void processMemberProgress(Member loginMember) {
        int totalScore = scoreService.calculateAndSaveDiagnosisScores(loginMember.getId());
        badgeService.awardBadgesForStandardScores(loginMember);
        loginMember.updateCharacterType(totalScore);
    }

    private List<DiagnosisResult> processDiagnosisResults(
        List<DiagnosisQuestion> questions,
        Member loginMember,
        List<CategoryWithDiagnosis> request
    ) {
        Map<Long, DiagnosisResult> existingResults = mapExistingResults(questions, loginMember);

        return questions.stream()
            .flatMap(question -> request.stream()
                .map(categoryWithDiagnosis ->
                    updateOrCreateDiagnosisResult(
                        question, loginMember, categoryWithDiagnosis, existingResults
                    )
                )
            )
            .toList();
    }

    private Map<Long, DiagnosisResult> mapExistingResults(
        List<DiagnosisQuestion> questions,
        Member loginMember
    ) {
        return resultRepository.findByMemberAndQuestions(questions, loginMember).stream()
            .collect(Collectors.toMap(
                result -> result.getQuestion().getId(),
                result -> result
            ));
    }

    private DiagnosisResult updateOrCreateDiagnosisResult(
        DiagnosisQuestion question,
        Member loginMember,
        CategoryWithDiagnosis request,
        Map<Long, DiagnosisResult> existingResults
    ) {
        DiagnosisResult result = existingResults.get(question.getId());
        Boolean answer = request.questionResult();

        if (result != null) {
            result.updateAnswerType(AnswerType.from(answer));
            return result;
        }
        return createDiagnosisResult(question, loginMember, answer);
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

    public DiagnosisResultWrapper getDiagnosisResult() {
        Member loginMember = getLoginMember();

        String characterName = loginMember.getCharacterType().getKor();
        List<Category> categories = categoryRepository.findAll();

        List<CalculateDiagnosisResult> results = scoreService.calculateScore(categories,
            loginMember.getId());
        List<DetailResponse> detailResponses = programService.recommendRandomProgram(results);

        return DiagnosisResultWrapper.builder()
            .characterType(characterName)
            .results(results)
            .programs(detailResponses)
            .build();
    }

    @Transactional
    public DiagnosisResultDTO getDiagnosisResultWithBadges() {
        Member loginMember = getLoginMember();

        String characterName = loginMember.getCharacterType().getKor();
        List<Category> categories = categoryRepository.findAll();

        List<CalculateDiagnosisResult> results = scoreService.calculateScore(categories,
            loginMember.getId());
        List<BadgeType> badges = badgeService.getBadgeTypeByMember(loginMember);

        return DiagnosisResultDTO.builder()
            .characterType(characterName)
            .results(results)
            .badges(badges)
            .build();
    }

    private Member getLoginMember() {
        return memberRepository.findById(TokenService.getLoginMemberId());
    }
}
