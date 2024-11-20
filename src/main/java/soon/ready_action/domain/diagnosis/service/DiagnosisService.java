package soon.ready_action.domain.diagnosis.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.category.repository.CategoryRepository;
import soon.ready_action.domain.diagnosis.dto.request.CategoryWithDiagnosisRequest;
import soon.ready_action.domain.diagnosis.dto.response.OnboardingQuestionResponse;
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
public class DiagnosisService {

    private final DiagnosisQuestionRepository questionRepository;
    private final DiagnosisResultRepository resultRepository;
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
    public void saveOnboardingDiagnosisResults(CategoryWithDiagnosisRequest request) {
        Member loginMember = memberRepository.findById(TokenService.getLoginMemberId());

        Map<Long, Boolean> questionResult = request.questionResult();

        List<DiagnosisQuestion> questions = questionRepository.findAllById(questionResult.keySet());

        List<DiagnosisResult> results = questions.stream()
            .map(question -> createDiagnosisResult(
                question, loginMember, questionResult.get(question.getId()))
            )
            .toList();

        resultRepository.saveAll(results);
    }

    private DiagnosisResult createDiagnosisResult(DiagnosisQuestion question,
        Member member, Boolean answer) {
        return DiagnosisResult.builder()
            .question(question)
            .member(member)
            .answerType(AnswerType.from(answer))
            .build();
    }
}