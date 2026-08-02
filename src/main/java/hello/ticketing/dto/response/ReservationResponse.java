package hello.ticketing.dto.response;

import hello.ticketing.domain.Reservation;
import hello.ticketing.domain.ReservationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationResponse(
        UUID id,
        Long userId,
        Long roundId,
        ReservationStatus status,
        int quantity,
        LocalDateTime reservedAt,
        LocalDateTime expiresAt,
        LocalDateTime createdAt
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getUser().getId(),
                reservation.getRound().getId(),
                reservation.getStatus(),
                reservation.getQuantity(),
                reservation.getReservedAt(),
                reservation.getExpiresAt(),
                reservation.getCreatedAt()
        );
    }
}
