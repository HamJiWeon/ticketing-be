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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false, unique = true)
    private Reservation reservation;

    private UUID orderId;

    private String pgPaymentKey;

    private int totalPrice;

    @Column(name = "fail_code")
    private String code;

    @Column(name = "fail_message")
    private String message;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime approvedAt;

    @Version
    private Long version;

    @Builder
    private Payment(Reservation reservation, UUID orderId, int totalPrice) {
        this.reservation = reservation;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.status = PaymentStatus.READY;
    }

    public static Payment from(Reservation reservation, UUID orderId, int totalPrice) {
        return Payment.builder()
                .reservation(reservation)
                .orderId(orderId)
                .totalPrice(totalPrice)
                .build();
    }

    public void approve(String pgPaymentKey, LocalDateTime approvedAt) {
        if (this.status != PaymentStatus.READY) {
            throw new IllegalStateException("승인 가능한 상태가 아닙니다.");
        }
        this.status = PaymentStatus.DONE;
        this.pgPaymentKey = pgPaymentKey;
        this.approvedAt = approvedAt;
    }

    public void fail(String code, String message) {
        if (this.status != PaymentStatus.READY) {
            throw new IllegalStateException("실패 처리할 수 있는 상태가 아닙니다.");
        }
        this.status = PaymentStatus.FAILED;
        this.code = code;
        this.message = message;
    }

    public void cancel() {
        if (this.status != PaymentStatus.DONE) {
            throw new IllegalStateException("취소 가능한 상태가 아닙니다.");
        }
        this.status = PaymentStatus.CANCELED;
    }
}
