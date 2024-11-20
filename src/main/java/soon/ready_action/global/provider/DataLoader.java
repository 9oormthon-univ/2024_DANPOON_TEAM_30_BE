package soon.ready_action.global.provider;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import soon.ready_action.global.loader.CategoryLoader;
import soon.ready_action.global.loader.QuestionLoader;

@RequiredArgsConstructor
@Configuration
public class DataLoader {

    private final CategoryLoader categoryLoader;
    private final QuestionLoader questionLoader;

    @PostConstruct
    public void loadData() {
        categoryLoader.loadCategory();
        questionLoader.loadQuestions();
    }
}