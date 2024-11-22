package soon.ready_action.domain.diagnosis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.category.repository.CategoryRepository;
import soon.ready_action.domain.diagnosis.dto.CalculateDiagnosisResult;
import soon.ready_action.domain.diagnosis.dto.request.CategoryWithDiagnosisRequest;
import soon.ready_action.domain.diagnosis.dto.request.DiagnosisQuestionPaginationRequest;
import soon.ready_action.domain.diagnosis.dto.response.DiagnosisQuestionPaginationResponseWrapper;
import soon.ready_action.domain.diagnosis.dto.response.DiagnosisQuestionResponse;
import soon.ready_action.domain.diagnosis.dto.response.OnboardingQuestionResponse;
import soon.ready_action.domain.diagnosis.entity.AnswerType;
import soon.ready_action.domain.diagnosis.entity.DiagnosisCategoryScore;
import soon.ready_action.domain.diagnosis.entity.DiagnosisQuestion;
import soon.ready_action.domain.diagnosis.entity.DiagnosisResult;
import soon.ready_action.domain.diagnosis.repository.DiagnosisCategoryScoreRepository;
import soon.ready_action.domain.diagnosis.repository.DiagnosisQuestionRepository;
import soon.ready_action.domain.diagnosis.repository.DiagnosisResultRepository;
import soon.ready_action.domain.member.entity.Member;
import soon.ready_action.domain.member.repository.MemberRepository;
import soon.ready_action.global.oauth2.service.TokenService;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiagnosisService {

    private final DiagnosisQuestionRepository questionRepository;
    private final DiagnosisResultRepository resultRepository;
    private final DiagnosisCategoryScoreRepository scoreRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    public List<OnboardingQuestionResponse> getOnboardingQuestionResponseByCategories(
        List<String> categoriesAsString
    ) {
        List<Category> categories = categoryRepository.findCategoriesByTitles(categoriesAsString);

        return categories.stream()
            .map(this::buildOnboardingResponseForCategory)
            .toList();
    }

    private OnboardingQuestionResponse buildOnboardingResponseForCategory(Category category) {
        DiagnosisQuestion question = questionRepository.findByOnboardingQuestionForCategory(
            category);

        return OnboardingQuestionResponse.builder()
            .questionId(question.getId())
            .category(category.getTitle())
            .content(question.getContent())
            .build();
    }

    @Transactional
    public void saveDiagnosisResults(CategoryWithDiagnosisRequest request) {
        Member loginMember = memberRepository.findById(TokenService.getLoginMemberId());

        Map<Long, Boolean> questionResult = request.questionResult();
        List<DiagnosisQuestion> questions = questionRepository.findAllById(questionResult.keySet());

        List<DiagnosisResult> results = processDiagnosisResults(
            questions, loginMember, questionResult
        );

        resultRepository.saveAll(results);
        CalculateAndSaveDiagnosisScores();
    }

    private List<DiagnosisResult> processDiagnosisResults(
        List<DiagnosisQuestion> questions,
        Member loginMember,
        Map<Long, Boolean> questionResult
    ) {
        Map<Long, DiagnosisResult> existingResults = resultRepository.findByMemberAndQuestions(
                questions, loginMember
            ).stream()
            .collect(Collectors.toMap(
                result -> result.getQuestion().getId(),
                result -> result
            ));

        return questions.stream()
            .map(question -> updateOrCreateDiagnosisResult(
                loginMember, questionResult, question, existingResults
            )).toList();
    }

    private DiagnosisResult updateOrCreateDiagnosisResult(
        Member loginMember,
        Map<Long, Boolean> questionResult, DiagnosisQuestion question,
        Map<Long, DiagnosisResult> existingResults
    ) {
        DiagnosisResult result = existingResults.get(question.getId());
        if (result != null) {
            result.updateAnswerType(AnswerType.from(questionResult.get(question.getId())));
            return result;
        } else {
            return createDiagnosisResult(question, loginMember,
                questionResult.get(question.getId()));
        }
    }

    @Transactional
    public void CalculateAndSaveDiagnosisScores() {
        Long loginMemberId = TokenService.getLoginMemberId();
        List<Category> categories = categoryRepository.findAll();
        Map<String, Category> categoryMap = categories.stream()
            .collect(Collectors.toMap(Category::getTitle, category -> category));

        List<CalculateDiagnosisResult> calculateDiagnosisResults = calculateScore(
            categories, loginMemberId
        );

        List<DiagnosisCategoryScore> existingScores = scoreRepository.findByMemberId(loginMemberId);

        Map<Long, DiagnosisCategoryScore> existingScoresMap = toScoreMap(existingScores);

        List<DiagnosisCategoryScore> scoresToSave = calculateDiagnosisResults.stream()
            .filter(result -> result.score() >= 8)
            .map(result -> processScore(result, categoryMap, existingScoresMap, loginMemberId))
            .collect(Collectors.toList());

        scoreRepository.saveAll(scoresToSave);
    }

    @Transactional(readOnly = true)
    public List<CalculateDiagnosisResult> calculateScore(
        List<Category> categories, Long memberId
    ) {
        List<CalculateDiagnosisResult> results = new ArrayList<>();

        categories.forEach(category -> {
            List<DiagnosisResult> resultsByCategory = resultRepository
                .findDiagnosisResultsByMemberAndCategoryAndAnswerType(
                    memberId, category.getTitle()
                );

            int count = resultsByCategory.size();

            results.add(
                CalculateDiagnosisResult.builder()
                    .score(count)
                    .categoryTitle(category.getTitle())
                    .build()
            );
        });

        return results;
    }

    public DiagnosisQuestionPaginationResponseWrapper getPagedDiagnosisQuestion(
        DiagnosisQuestionPaginationRequest request
    ) {
        List<DiagnosisQuestionResponse> pagedDiagnosisQuestion = questionRepository.getPagedDiagnosisQuestion(
            request.lastQuestionId(), TokenService.getLoginMemberId(), request.categoryTitle()
        );

        boolean determineHasNextPage = questionRepository.determineHasNextPage(
            pagedDiagnosisQuestion
        );

        return DiagnosisQuestionPaginationResponseWrapper.builder()
            .questions(pagedDiagnosisQuestion)
            .hasNext(determineHasNextPage)
            .build();
    }

    private DiagnosisResult createDiagnosisResult(
        DiagnosisQuestion question, Member member, Boolean answer
    ) {
        return DiagnosisResult.builder()
            .question(question)
            .member(member)
            .answerType(AnswerType.from(answer))
            .build();
    }

    private Map<Long, DiagnosisCategoryScore> toScoreMap(
        List<DiagnosisCategoryScore> existingScores) {
        return existingScores.stream()
            .collect(Collectors.toMap(
                DiagnosisCategoryScore::getCategoryId,
                score -> score,
                (existing, replacement) -> existing
            ));
    }

    private DiagnosisCategoryScore processScore(
        CalculateDiagnosisResult result,
        Map<String, Category> categoryMap,
        Map<Long, DiagnosisCategoryScore> existingScoresMap,
        Long loginMemberId
    ) {
        Category category = categoryMap.get(result.categoryTitle());
        DiagnosisCategoryScore existingScore = existingScoresMap.get(category.getId());

        if (existingScore != null) {
            existingScore.updateScore(result.score());
            return existingScore;
        } else {
            return DiagnosisCategoryScore.builder()
                .score(result.score())
                .categoryId(category.getId())
                .memberId(loginMemberId)
                .build();
        }
    }
}