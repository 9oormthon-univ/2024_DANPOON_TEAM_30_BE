package soon.ready_action.global.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import soon.ready_action.global.exception.enums.ErrorCode;

@Getter
@RequiredArgsConstructor
public abstract class ReadyActionException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    private final ErrorCode errorCode;
}
