package hello.ticketing.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
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
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private UUID orderId;

    private String pgPaymentKey;

    private String pgProvider;

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
}
