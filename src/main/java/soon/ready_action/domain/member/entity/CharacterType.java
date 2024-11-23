package soon.ready_action.domain.member.entity;

import java.util.Arrays;
import java.util.function.IntPredicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import soon.ready_action.global.exception.CharacterTypeNotFoundException;

@RequiredArgsConstructor
@Getter
public enum CharacterType {
    COOL("ONE", value -> value < 10),
    HANDY("TWO", value -> value < 20),
    EXCITED("THREE", value -> value < 30),
    GENIUS("FOUR", value -> value < 40),
    PROUD("FIVE", value -> value >= 40);

    private final String kor;
    private final IntPredicate condition;

    public static CharacterType of(int value) {
        return Arrays.stream(values())
            .filter(type -> type.condition.test(value))
            .findFirst()
            .orElseThrow(CharacterTypeNotFoundException::new);
    }
}
