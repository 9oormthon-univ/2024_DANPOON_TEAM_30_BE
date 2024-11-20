package soon.ready_action.global.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;
import soon.ready_action.domain.diagnosis.dto.QuestionDataLoadDto;

@Getter
@Component
public class CustomObjectMapperProvider {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String writeValueAsString(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }

    public List<QuestionDataLoadDto> loadQuestionDataFromJson() {
        try (InputStream inputStream = getClass().getResourceAsStream("/data.json")) {
            return objectMapper.readValue(inputStream,
                new TypeReference<List<QuestionDataLoadDto>>() {
                });
        } catch (IOException e) {
            throw new RuntimeException("Failed to load questions.json", e);
        }
    }
}
