package soon.ready_action.global.exception;

import soon.ready_action.global.exception.enums.ErrorCode;

public class AnswerTypeNotFoundException extends ReadyActionException {

    public AnswerTypeNotFoundException() {
        super(ErrorCode.ANSWER_TYPE_NOT_FOUND);
    }
}
