package hello.ticketing.repository;

import hello.ticketing.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Long, Payment> {
}
