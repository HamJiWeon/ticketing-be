package hello.ticketing.dto.request;

import hello.ticketing.domain.GenreStatus;
import hello.ticketing.domain.LimitStatus;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record PerformanceUpdateRequest(

        String name,

        LocalDate startAt,

        LocalDate endAt,

        String place,

        @PositiveOrZero(message = "가격은 0원 이상이어야 합니다.")
        Integer price,

        GenreStatus genre,

        LimitStatus ticketLimit
) {
}
