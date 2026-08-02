package hello.ticketing.service;

import hello.ticketing.domain.Performance;
import hello.ticketing.dto.request.PerformanceCreateRequest;
import hello.ticketing.dto.response.PerformanceResponse;
import hello.ticketing.mapper.PerformanceMapper;
import hello.ticketing.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final PerformanceMapper performanceMapper;

    @Override
    public PerformanceResponse create(PerformanceCreateRequest request) {
        if(performanceRepository.existsByNameAndPlaceAndGenre(request.name(), request.place(), request.genre())) {
            throw new IllegalArgumentException("이미 존재하는 공연입니다.");
        }

        Performance performance = Performance.builder()
                .name(request.name())
                .place(request.place())
                .price(request.price())
                .startAt(request.startAt())
                .endAt(request.endAt())
                .genre(request.genre())
                .ticketLimit(request.ticketLimit())
                .build();

        Performance save = performanceRepository.save(performance);
        return performanceMapper.toDto(save);
    }
}
