package hello.ticketing.repository;

import hello.ticketing.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Long, Reservation> {
}
