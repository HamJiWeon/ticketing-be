package hello.ticketing.service;

import hello.ticketing.domain.*;
import hello.ticketing.dto.request.PerformanceCreateRequest;
import hello.ticketing.dto.response.PerformanceResponse;
import hello.ticketing.global.exception.performance.InvalidPerformancePeriodException;
import hello.ticketing.global.exception.performance.PerformanceNotFoundException;
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
            throw new InvalidPerformancePeriodException(request.startAt());
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
                .orElseThrow(() -> new PerformanceNotFoundException(perfId));
        if(performance.getPerfStatus()==PerformanceStatus.CLOSED) {
            throw new PerformanceNotFoundException(perfId);
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
                .orElseThrow(() -> new PerformanceNotFoundException(perfId));

        reservationRepository.cancelAllByPerformanceId(performance.getId(), ReservationStatus.CANCELED, LocalDateTime.now());
        Performance perf = performanceRepository.findById(perfId)
                .orElseThrow(() -> new PerformanceNotFoundException(perfId));
        perf.changeStatus(PerformanceStatus.CLOSED);
    }
}
