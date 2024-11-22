package soon.ready_action.domain.badge.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BadgeType {

    HEALTH("건강"),
    RESIDENCE("주거"),
    EDUCATION("교육"),
    FINANCE("금융"),
    EMPLOYMENT("취업");

    private final String type;
}
