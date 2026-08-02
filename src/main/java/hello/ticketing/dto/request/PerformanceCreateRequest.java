package hello.ticketing.dto.request;

import hello.ticketing.domain.LimitStatus;

import java.time.LocalDate;

public record PerformanceCreateRequest(

        String name,
        LocalDate startAt,
        LocalDate endAt,
        String place,
        int price,
        LimitStatus status
) {
}
