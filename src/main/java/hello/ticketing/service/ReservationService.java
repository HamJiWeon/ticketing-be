package hello.ticketing.service;

import hello.ticketing.domain.Reservation;
import hello.ticketing.dto.request.CreateReservationRequest;
import hello.ticketing.dto.response.ReservationResponse;

public interface ReservationService {

    ReservationResponse create(Long userId, Long roundId, int quantity);
}
