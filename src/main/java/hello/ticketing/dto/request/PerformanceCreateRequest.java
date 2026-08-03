package hello.ticketing.dto.request;

import hello.ticketing.domain.GenreStatus;
import hello.ticketing.domain.LimitStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record PerformanceCreateRequest(

        @NotBlank(message = "공연 이름은 필수입니다.")
        String name,

        @NotNull(message = "공연 시작일은 필수입니다.")
        LocalDate startAt,

        @NotNull(message = "공연 종료일은 필수입니다.")
        LocalDate endAt,

        @NotBlank(message = "공연 장소는 필수입니다.")
        String place,

        @PositiveOrZero(message = "가격은 0원 이상이어야 합니다.")
        int price,

        @NotNull(message = "공연 장르는 필수입니다.")
        GenreStatus genre,

        @NotNull(message = "매수 제한은 필수입니다.")
        LimitStatus ticketLimit
) {
}
