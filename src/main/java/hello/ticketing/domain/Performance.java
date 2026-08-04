package hello.ticketing.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private LocalDate startAt;

    @Column(nullable = false)
    private LocalDate endAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenreStatus genre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LimitStatus ticketLimit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerformanceStatus perfStatus;

    @OneToMany(mappedBy = "performance")
    private List<Round> round;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Performance(Long id, String name, String place, int price, LocalDate startAt, LocalDate endAt, GenreStatus genre, LimitStatus ticketLimit, PerformanceStatus perfStatus, List<Round> round, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.price = price;
        this.startAt = startAt;
        this.endAt = endAt;
        this.genre = genre;
        this.ticketLimit = ticketLimit;
        this.perfStatus = perfStatus;
        this.round = round;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void changeStatus(PerformanceStatus perfStatus) {
        this.perfStatus = perfStatus;
    }
}