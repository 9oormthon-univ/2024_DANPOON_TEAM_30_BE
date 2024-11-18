package soon.ready_action.global.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ObjectMapperProvider {

    private final ObjectMapper objectMapper = new ObjectMapper();
}
