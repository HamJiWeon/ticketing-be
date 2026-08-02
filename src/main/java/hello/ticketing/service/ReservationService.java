package hello.ticketing.service;

import hello.ticketing.dto.response.ReservationResponse;

public interface ReservationService {

    ReservationResponse create(Long userId, Long roundId, int quantity);
}
