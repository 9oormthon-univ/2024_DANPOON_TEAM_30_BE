package soon.ready_action.domain.chatbot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.ready_action.domain.chatbot.entity.QuestionType;

@Getter
@AllArgsConstructor
public class ChatbotRequest {

    private int questionType;
    private int depth;

    public QuestionType getQuestionTypeEnum() {
        return QuestionType.values()[questionType];
    }
}