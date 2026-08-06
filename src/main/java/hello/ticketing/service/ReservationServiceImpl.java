package hello.ticketing.service;

import hello.ticketing.domain.Remain;
import hello.ticketing.domain.Reservation;
import hello.ticketing.domain.Round;
import hello.ticketing.domain.User;
import hello.ticketing.dto.response.ReservationResponse;
import hello.ticketing.repository.RemainRepository;
import hello.ticketing.repository.ReservationRepository;
import hello.ticketing.repository.RoundRepository;
import hello.ticketing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static hello.ticketing.domain.ReservationStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private static final long HOLD_MINUTES = 5;

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoundRepository roundRepository;
    private final RemainRepository remainRepository;

    @Override
    public ReservationResponse create(Long userId, Long roundId, int quantity) {

        Remain remain = remainRepository.findByRoundIdForUpdate(roundId)
                .orElseThrow(() -> new IllegalArgumentException("재고 정보가 없습니다. roundId: " + roundId));

        remain.decrease(quantity);

        User user = findByUserId(userId);
        Round round = findByRoundId(roundId);
        LocalDateTime now = LocalDateTime.now();

        Reservation reservation = Reservation.from(
                user,
                round,
                quantity,
                now.plusMinutes(HOLD_MINUTES),
                PENDING
        );

        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationResponse.from(savedReservation);
    }

    @Override
    public Page<ReservationResponse> gets(Long userId, Pageable pageable) {
        PageRequest sorted = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Order.desc("reservedAt"),
                        Sort.Order.asc("id"))
        );

        return reservationRepository.findByUser_IdWithPerformance(userId, sorted)
                .map(ReservationResponse::from);
    }

    @Override
    public ReservationResponse cancel(UUID reservationId) {
        Reservation reservation = reservationRepository.findByIdForUpdate(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약 정보가 없습니다. ID: " + reservationId));

        reservation.cancelReservation();

        Remain remain = remainRepository.findByRoundIdForUpdate(reservation.getRound().getId())
                .orElseThrow(() -> new IllegalArgumentException("재고 정보가 없습니다. ID: " + reservation.getRound().getId()));

        remain.increase(reservation.getQuantity());

        return ReservationResponse.from(reservation);
    }

    private @NonNull Round findByRoundId(Long roundId) {
        return roundRepository.findById(roundId)
                .orElseThrow(() -> new IllegalArgumentException("시간대가 없습니다. ID: " + roundId));
    }

    private @NonNull User findByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 없습니다. ID: " + userId));
    }
}
