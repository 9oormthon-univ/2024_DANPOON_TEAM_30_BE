package soon.ready_action.global.exception;

import soon.ready_action.global.exception.enums.ErrorCode;

public class DiagnosisQuestionNotFoundException extends ReadyActionException {

    public DiagnosisQuestionNotFoundException() {
        super(ErrorCode.DIAGNOSIS_ONBOARDING_QUESTION_NOT_FOUND);
    }
}
