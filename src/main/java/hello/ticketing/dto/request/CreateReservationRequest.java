package hello.ticketing.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateReservationRequest(

        @NotNull(message = "유저 값은 필수 입니다.")
        Long userId,

        @NotNull(message = "회차는 필수 입니다.")
        Long roundId,

        @NotNull(message = "수량은 필수 입니다.")
        @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
        int quantity
) {
}
