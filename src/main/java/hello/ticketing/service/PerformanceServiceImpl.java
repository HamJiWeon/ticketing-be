package hello.ticketing.service;

import hello.ticketing.domain.*;
import hello.ticketing.dto.request.PerformanceCreateRequest;
import hello.ticketing.dto.response.PerformanceResponse;
import hello.ticketing.mapper.PerformanceMapper;
import hello.ticketing.repository.PerformanceRepository;
import hello.ticketing.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final PerformanceMapper performanceMapper;

    @Override
    public PerformanceResponse create(PerformanceCreateRequest request) {
        if(request.endAt().isBefore(request.startAt())) {
            throw new IllegalArgumentException("공연 종료일은 시작일보다 빠를 수 없습니다.");
        }

        Performance performance = Performance.builder()
                .name(request.name())
                .place(request.place())
                .price(request.price())
                .startAt(request.startAt())
                .endAt(request.endAt())
                .genre(request.genre())
                .ticketLimit(request.ticketLimit())
                .perfStatus(PerformanceStatus.OPEN)
                .build();

        Performance save = performanceRepository.save(performance);
        return performanceMapper.toDto(save);
    }

    @Override
    public PerformanceResponse findById(Long perfId) {
        Performance performance = performanceRepository.findById(perfId)
                // 나중에 커스텀 예외로 변경 예정
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 공연입니다."));
        if(performance.getPerfStatus()==PerformanceStatus.CLOSED) {
            throw new IllegalStateException("종료된 공연입니다.");
        }

        return performanceMapper.toDto(performance);
    }

    @Override
    public List<PerformanceResponse> findAll(String keyword, Pageable pageable) {
        Page<Performance> performances;
        if(keyword==null || keyword.isBlank()) {
            performances = performanceRepository.findAllByPerfStatus(PerformanceStatus.OPEN, pageable);
        }
        else {
            performances = performanceRepository.findAllByPerfStatusAndNameContainingIgnoreCase(PerformanceStatus.OPEN,keyword, pageable);
        }

        return performances.getContent().stream().map(performanceMapper::toDto).toList();
    }

    @Override
    public void delete(Long perfId) {
        Performance performance = performanceRepository.findById(perfId)
                // 나중에 커스텀 예외로 변경 예정
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 공연입니다."));

        reservationRepository.cancelAllByPerformanceId(performance.getId(), ReservationStatus.CANCELED, LocalDateTime.now());
        Performance perf = performanceRepository.findById(perfId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 공연입니다."));
        perf.changeStatus(PerformanceStatus.CLOSED);
    }
}
