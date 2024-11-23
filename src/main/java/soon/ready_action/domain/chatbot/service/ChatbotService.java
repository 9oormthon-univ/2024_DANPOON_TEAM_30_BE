package soon.ready_action.domain.chatbot.service;

import soon.ready_action.domain.chatbot.entity.Chatbot;
import soon.ready_action.domain.chatbot.entity.QuestionType;
import soon.ready_action.domain.chatbot.dto.request.ChatbotRequest;
import soon.ready_action.domain.chatbot.repository.ChatbotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatbotService {

    private final ChatbotRepository chatbotRepository;

    public ChatbotService(ChatbotRepository chatbotRepository) {
        this.chatbotRepository = chatbotRepository;
    }

    @Transactional
    public List<Chatbot> getChatbotResponses(ChatbotRequest requestDto) {
        // 요청에서 받은 정수 값을 QuestionType 열거형으로 변환
        QuestionType questionType = requestDto.getQuestionTypeEnum();

        // 변환된 questionType과 depth를 사용하여 DB 조회
        List<Chatbot> responses = chatbotRepository.findByQuestionTypeAndDepth(questionType, requestDto.getDepth());

        return responses;
    }
}