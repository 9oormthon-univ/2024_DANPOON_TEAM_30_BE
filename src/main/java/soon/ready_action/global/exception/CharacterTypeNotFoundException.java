package soon.ready_action.global.exception;

import soon.ready_action.global.exception.enums.ErrorCode;

public class CharacterTypeNotFoundException extends ReadyActionException {

    public CharacterTypeNotFoundException() {
        super(ErrorCode.CHARACTER_TYPE_NOT_FOUND);
    }
}
