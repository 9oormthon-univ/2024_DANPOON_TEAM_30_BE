package soon.ready_action.domain.diagnosis.entity;

import lombok.Getter;
import soon.ready_action.global.exception.AnswerTypeNotFoundException;

@Getter
public enum AnswerType {

    O_SELECTED(true), X_SELECTED(false), NOT_SELECTED(null);

    private final Boolean selected;

    AnswerType(Boolean selected) {
        this.selected = selected;
    }

    public static AnswerType from(Boolean selected) {
        for (AnswerType answerType : values()) {
            if (answerType.selected == selected) { // null-safe 비교
                return answerType;
            }
        }
        throw new AnswerTypeNotFoundException();
    }
}