package soon.ready_action.global.loader;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.ready_action.domain.category.entity.Category;
import soon.ready_action.domain.category.repository.CategoryRepository;
import soon.ready_action.domain.diagnosis.dto.QuestionDataLoadDto;
import soon.ready_action.domain.diagnosis.repository.DiagnosisQuestionRepository;
import soon.ready_action.global.provider.CustomObjectMapperProvider;

@RequiredArgsConstructor
@Service
public class QuestionLoader {

    private final CategoryRepository categoryRepository;
    private final DiagnosisQuestionRepository diagnosisQuestionRepository;
    private final CustomObjectMapperProvider objectMapperProvider;

    @Transactional
    public void loadQuestions() {
        if (diagnosisQuestionRepository.count() == 0) {
            List<QuestionDataLoadDto> questionDataLoadDtos = objectMapperProvider.loadQuestionDataFromJson();

            for (QuestionDataLoadDto dto : questionDataLoadDtos) {
                Category category = categoryRepository.findByTitle(dto.category());
                diagnosisQuestionRepository.onboardingQuestionSave(dto.onboardingQuestion(),
                    category);

                Map<String, Boolean> questions = dto.questions();
                diagnosisQuestionRepository.questionsSave(questions, category);
            }
        }
    }
}
