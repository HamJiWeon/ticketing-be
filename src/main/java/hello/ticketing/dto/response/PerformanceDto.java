package hello.ticketing.dto.response;

import hello.ticketing.domain.LimitStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PerformanceDto(
        Long id,
        String name,
        LocalDate startAt,
        LocalDate endAt,
        String place,
        int price,
        LimitStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
