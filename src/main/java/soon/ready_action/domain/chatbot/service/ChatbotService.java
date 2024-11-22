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
        QuestionType questionType = QuestionType.valueOf(requestDto.getQuestionType().toUpperCase());

        List<Chatbot> responses = chatbotRepository.findByQuestionTypeAndDepth(questionType, requestDto.getDepth());

        return responses;
    }
}
