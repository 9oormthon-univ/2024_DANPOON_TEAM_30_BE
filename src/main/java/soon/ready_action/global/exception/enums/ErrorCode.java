package soon.ready_action.global.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    MEMBER_NOT_FOUND(404, "멤버를 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(404, "카테고리를 찾을 수 없습니다."),
    ANSWER_TYPE_NOT_FOUND(400, "잘못된 요청값입니다."),
    DIAGNOSIS_ONBOARDING_QUESTION_NOT_FOUND(404, "온보딩 질문을 찾을 수 없습니다."),
    CHARACTER_TYPE_NOT_FOUND(404, "캐릭터 타입을 찾을 수 없습니다."),
    BADGE_NOT_FOUND(404, "배지를 찾을 수 없습니다."),
    AUTHORIZATION_DENIED(403, "접근이 거부되었습니다. 권한을 확인하세요."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다.");

    private final int status;
    private final String message;
}
