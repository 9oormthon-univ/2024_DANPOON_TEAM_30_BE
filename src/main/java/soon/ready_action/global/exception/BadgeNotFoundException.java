package soon.ready_action.global.exception;

import soon.ready_action.global.exception.enums.ErrorCode;

public class BadgeNotFoundException extends ReadyActionException {

    public BadgeNotFoundException() {
        super(ErrorCode.BADGE_NOT_FOUND);
    }
}
