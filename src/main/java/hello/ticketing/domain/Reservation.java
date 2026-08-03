package hello.ticketing.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation implements Persistable<UUID> {

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
        this.updatedAt = this.reservedAt;
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

    public void cancelReservation() {
        if (this.status == ReservationStatus.CANCELED) {
            throw new IllegalStateException("이미 취소된 예약입니다.");
        }
        this.status = ReservationStatus.CANCELED;
        this.updatedAt = LocalDateTime.now();
    }

    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostPersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
}