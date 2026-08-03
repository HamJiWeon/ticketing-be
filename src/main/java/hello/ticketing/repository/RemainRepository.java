package hello.ticketing.repository;

import hello.ticketing.domain.Remain;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface RemainRepository extends JpaRepository<Remain, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from Remain r where r.roundId = :roundId")
    Optional<Remain> findByRoundIdForUpdate(Long roundId);
}
