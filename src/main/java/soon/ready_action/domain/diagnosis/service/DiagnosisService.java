package soon.ready_action.domain.diagnosis.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.category.repository.CategoryRepository;
import soon.ready_action.domain.diagnosis.dto.response.OnboardingQuestionResponse;
import soon.ready_action.domain.diagnosis.entity.DiagnosisQuestion;
import soon.ready_action.domain.diagnosis.repository.DiagnosisQuestionRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiagnosisService {

    private final DiagnosisQuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;

    public List<OnboardingQuestionResponse> getOnboardingQuestionResponseByCategories(
        List<String> categoriesAsString
    ) {
        List<Category> categories = categoryRepository.findCategoriesByTitles(categoriesAsString);

        return categories.stream()
            .map(this::buildOnboardingResponseForCategory)
            .toList();
    }

    private OnboardingQuestionResponse buildOnboardingResponseForCategory(Category category) {
        DiagnosisQuestion question = questionRepository.findByOnboardingQuestionForCategory(category);

        return OnboardingQuestionResponse.builder()
            .category(category.getTitle())
            .content(question.getContent())
            .build();
    }
}