package hello.ticketing.repository;

import hello.ticketing.domain.Reservation;
import hello.ticketing.domain.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    Page<Reservation> findByUser_Id(Long userId, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("""
            update Reservation r
            set r.status = :status,
            r.updatedAt = :updatedAt
            where r.round.performance.id = :perfId
            and r.status <> :status
            """)
    int cancelAllByPerformanceId(@Param("perfId") Long perfId,
                                 @Param("status") ReservationStatus status,
                                 @Param("updatedAt")LocalDateTime updatedAt);
}
