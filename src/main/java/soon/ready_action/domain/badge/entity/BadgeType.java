package soon.ready_action.domain.badge.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import soon.ready_action.global.exception.BadgeNotFoundException;

@Getter
@RequiredArgsConstructor
public enum BadgeType {

    HEALTH("건강"),
    RESIDENCE("주거"),
    EDUCATION("교육"),
    FINANCE("금융"),
    EMPLOYMENT("취업");

    private final String type;

    public static BadgeType of(String type) {
        for (BadgeType value : values()) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        throw new BadgeNotFoundException();
    }
}
