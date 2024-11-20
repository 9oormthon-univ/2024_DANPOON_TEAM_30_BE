package soon.ready_action.global.exception;

import static soon.ready_action.global.exception.enums.ErrorCode.CATEGORY_NOT_FOUND;

public class CategoryNotFoundException extends ReadyActionException {

    public CategoryNotFoundException() {
        super(CATEGORY_NOT_FOUND);
    }
}
