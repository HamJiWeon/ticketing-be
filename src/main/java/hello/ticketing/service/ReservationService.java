package hello.ticketing.service;

import hello.ticketing.dto.response.ReservationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReservationService {

    ReservationResponse create(Long userId, Long roundId, int quantity);

    Page<ReservationResponse> gets(Long userId, Pageable pageable);

    ReservationResponse cancel(UUID reservationId);
}
