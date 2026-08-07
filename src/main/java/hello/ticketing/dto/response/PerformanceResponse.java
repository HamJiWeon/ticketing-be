package hello.ticketing.dto.response;

import hello.ticketing.domain.GenreStatus;
import hello.ticketing.domain.LimitStatus;
import hello.ticketing.domain.PerformanceStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PerformanceResponse(
        Long id,
        String name,
        LocalDate startAt,
        LocalDate endAt,
        String place,
        int price,
        GenreStatus genre,
        LimitStatus ticketLimit,
        PerformanceStatus perfStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
