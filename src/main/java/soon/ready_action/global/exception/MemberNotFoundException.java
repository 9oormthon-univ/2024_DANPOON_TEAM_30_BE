package soon.ready_action.global.exception;

import soon.ready_action.global.exception.enums.ErrorCode;

public class MemberNotFoundException extends ReadyActionException {

    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
