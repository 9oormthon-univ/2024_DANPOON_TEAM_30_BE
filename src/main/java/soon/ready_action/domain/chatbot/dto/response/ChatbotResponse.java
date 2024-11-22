package soon.ready_action.domain.chatbot.dto.response;

import soon.ready_action.domain.chatbot.entity.Chatbot;
import soon.ready_action.domain.chatbot.entity.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ChatbotResponse {

    @Schema(description = "챗봇 문답 ID")
    private Long id;

    @Schema(description = "질문 내용")
    private String question;

    @Schema(description = "답변 내용")
    private String answer;

    @Schema(description = "질문 유형")
    private QuestionType questionType;

    @Schema(description = "질문 깊이")
    private int depth;

    public ChatbotResponse(Chatbot chatbot) {
        this.id = chatbot.getId();
        this.question = chatbot.getQuestion();
        this.answer = chatbot.getAnswer();
        this.questionType = chatbot.getQuestionType();
        this.depth = chatbot.getDepth();
    }
}
