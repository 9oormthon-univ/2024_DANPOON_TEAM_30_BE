package soon.ready_action.global.exception;

import soon.ready_action.global.exception.enums.ErrorCode;

public class RentalTypeNotFoundException extends ReadyActionException {

    public RentalTypeNotFoundException() {
        super(ErrorCode.RENTAL_TYPE_NOT_FOUND);
    }
}
