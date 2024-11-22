package soon.ready_action.global.exception;

import static soon.ready_action.global.exception.enums.ErrorCode.AUTHORIZATION_DENIED;

public class ForbiddenException extends ReadyActionException {

    public ForbiddenException() {
        super(AUTHORIZATION_DENIED);
    }
}
