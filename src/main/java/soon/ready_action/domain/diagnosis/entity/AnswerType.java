package soon.ready_action.domain.diagnosis.entity;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import soon.ready_action.global.exception.AnswerTypeNotFoundException;

@RequiredArgsConstructor
@Getter
public enum AnswerType {

    O_SELECTED(true), X_SELECTED(false), NOT_SELECTED(null);

    private final Boolean selected;

    public static AnswerType from(Boolean selected) {
        return Arrays.stream(values())
            .filter(answerType -> answerType.selected.equals(selected))
            .findAny()
            .orElseThrow(AnswerTypeNotFoundException::new);
    }
}