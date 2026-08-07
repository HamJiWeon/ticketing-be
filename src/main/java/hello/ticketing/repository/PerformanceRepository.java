package hello.ticketing.repository;

import hello.ticketing.domain.Performance;
import hello.ticketing.domain.PerformanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    Page<Performance> findAllByPerfStatus(PerformanceStatus status, Pageable pageable);

    Page<Performance> findAllByPerfStatusAndNameContainingIgnoreCase(PerformanceStatus status, String keyword, Pageable pageable);
}
