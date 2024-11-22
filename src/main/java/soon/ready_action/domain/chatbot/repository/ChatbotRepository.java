package soon.ready_action.domain.chatbot.repository;

import soon.ready_action.domain.chatbot.entity.Chatbot;
import soon.ready_action.domain.chatbot.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatbotRepository extends JpaRepository<Chatbot, Long> {

    List<Chatbot> findByQuestionTypeAndDepth(QuestionType questionType, int depth);
}
