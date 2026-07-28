package hello.ticketing.repository;

import hello.ticketing.domain.Remain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemainRepository extends JpaRepository<Long, Remain> {
}
