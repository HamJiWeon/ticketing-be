package hello.ticketing.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Reservation reservation;

    private UUID orderId;

    private String pgPaymentKey;

    private String pgProvider;

    private int totalPrice;

    @Column(name = "fail_code")
    private String code;

    @Column(name = "fail_message")
    private String message;

    private PaymentStatus status;

    @Version
    private Long version;
}
