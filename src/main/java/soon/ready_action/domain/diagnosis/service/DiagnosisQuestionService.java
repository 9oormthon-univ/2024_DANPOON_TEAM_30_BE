package soon.ready_action.domain.diagnosis.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.category.repository.CategoryRepository;
import soon.ready_action.domain.diagnosis.dto.request.DiagnosisQuestionPaginationRequest;
import soon.ready_action.domain.diagnosis.dto.response.DiagnosisQuestionPaginationResponseWrapper;
import soon.ready_action.domain.diagnosis.dto.response.DiagnosisQuestionResponse;
import soon.ready_action.domain.diagnosis.dto.response.OnboardingQuestionResponse;
import soon.ready_action.domain.diagnosis.entity.DiagnosisQuestion;
import soon.ready_action.domain.diagnosis.repository.DiagnosisQuestionRepository;
import soon.ready_action.global.oauth2.v1.service.TokenService;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiagnosisQuestionService {

    private final DiagnosisQuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;

    public List<OnboardingQuestionResponse> getOnboardingQuestionResponseByCategories(
        List<String> categoriesAsString
    ) {
        List<Category> categories = categoryRepository.findCategoriesByTitles(categoriesAsString);

        return categories.stream().map(this::buildOnboardingResponseForCategory).toList();
    }

    private OnboardingQuestionResponse buildOnboardingResponseForCategory(Category category) {
        DiagnosisQuestion question = questionRepository
            .findByOnboardingQuestionForCategory(category);

        return OnboardingQuestionResponse.builder()
            .questionId(question.getId())
            .category(category.getTitle())
            .content(question.getContent())
            .build();
    }

    @Transactional(readOnly = true)
    public DiagnosisQuestionPaginationResponseWrapper getPagedDiagnosisQuestion(
        Long lastQuestionId,
        String categoryTitle
    ) {
        List<DiagnosisQuestionResponse> pagedDiagnosisQuestion = questionRepository.getPagedDiagnosisQuestion(
            lastQuestionId, TokenService.getLoginMemberId(), categoryTitle
        );

        boolean hasNext = questionRepository.determineHasNextPage(pagedDiagnosisQuestion);

        return DiagnosisQuestionPaginationResponseWrapper.builder()
            .questions(pagedDiagnosisQuestion)
            .hasNext(hasNext)
            .build();
    }
}

