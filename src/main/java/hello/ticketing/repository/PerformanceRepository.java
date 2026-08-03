package hello.ticketing.repository;

import hello.ticketing.domain.GenreStatus;
import hello.ticketing.domain.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    boolean existsByNameAndPlaceAndGenre(String name, String place, GenreStatus genre);
}
