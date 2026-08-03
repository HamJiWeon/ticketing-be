package hello.ticketing.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Remain {

    @Id
    private Long roundId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "round_id")
    private Round round;

    private int totalSeat;

    private int remainSeat;

    public void decrease(int quantity) {
        if (this.remainSeat < quantity) {
            throw new IllegalStateException("좌석이 부족합니다. 남은 좌석: " + this.remainSeat);
        }

        this.remainSeat -= quantity;
    }
}
