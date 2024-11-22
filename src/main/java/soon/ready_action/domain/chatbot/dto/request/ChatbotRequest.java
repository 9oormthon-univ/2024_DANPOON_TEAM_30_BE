package soon.ready_action.domain.chatbot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatbotRequest {

    private String questionType;
    private int depth;
}
