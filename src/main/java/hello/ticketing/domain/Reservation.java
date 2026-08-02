package hello.ticketing.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id")
    private Round round;

    private int quantity;

    private LocalDateTime reservedAt;

    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private LocalDateTime updatedAt;

    @Builder
    private Reservation(
            User user,
            Round round,
            int quantity,
            LocalDateTime expiresAt,
            ReservationStatus status
    ) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.round = round;
        this.quantity = quantity;
        this.reservedAt = LocalDateTime.now();
        this.expiresAt = expiresAt;
        this.status = status;
    }

    public static Reservation from(User user, Round round, int quantity,
                                   LocalDateTime expiresAt, ReservationStatus status) {

        return Reservation.builder()
                .user(user)
                .round(round)
                .quantity(quantity)
                .expiresAt(expiresAt)
                .status(status)
                .build();
    }

    public void changeStatus(ReservationStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
}