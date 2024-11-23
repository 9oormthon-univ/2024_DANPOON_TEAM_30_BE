package soon.ready_action.domain.house.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import soon.ready_action.global.exception.RentalTypeNotFoundException;

@RequiredArgsConstructor
@Getter
public enum RentalType {

    MONTHLY_RENT("월세"),
    JEONSE("전세");

    private final String type;

    public static RentalType of(String type) {
        for (RentalType rentalType : values()) {
            if (rentalType.type.equals(type)) {
                return rentalType;
            }
        }
        throw new RentalTypeNotFoundException();
    }
}
