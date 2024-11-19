package soon.ready_action.global.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CustomObjectMapperProvider {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String writeValueAsString(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }
}
