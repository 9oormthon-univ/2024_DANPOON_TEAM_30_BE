package soon.ready_action.global.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    MEMBER_NOT_FOUND(404, "해당 ID로 멤버를 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(404, "해당 ID로 카테고리를 찾을 수 없습니다."),
    AUTHORIZATION_DENIED(403, "접근이 거부되었습니다. 권한을 확인하세요.");

    private final int status;
    private final String message;
}
