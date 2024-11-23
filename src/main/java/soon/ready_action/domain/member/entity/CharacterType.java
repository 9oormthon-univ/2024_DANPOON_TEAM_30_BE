package soon.ready_action.domain.member.entity;

import java.util.Arrays;
import java.util.function.IntPredicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import soon.ready_action.global.exception.CharacterTypeNotFoundException;

@RequiredArgsConstructor
@Getter
public enum CharacterType {
    COOL("쿨쿨이", value -> value < 10),
    HANDY("뚝딱뚝딱", value -> value < 20),
    EXCITED("두근두근", value -> value < 30),
    GENIUS("척척박사", value -> value < 40),
    PROUD("으쓱으쓱", value -> value >= 40);

    private final String kor;
    private final IntPredicate condition;

    public static CharacterType of(int value) {
        return Arrays.stream(values())
            .filter(type -> type.condition.test(value))
            .findFirst()
            .orElseThrow(CharacterTypeNotFoundException::new);
    }
}
