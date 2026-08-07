package hello.ticketing.service;

import hello.ticketing.dto.request.PerformanceCreateRequest;
import hello.ticketing.dto.request.PerformanceUpdateRequest;
import hello.ticketing.dto.response.PerformanceResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PerformanceService {

    PerformanceResponse create(PerformanceCreateRequest request);

    PerformanceResponse findById(Long perfId);

    List<PerformanceResponse> findAll(String keyword, Pageable pageable);

    PerformanceResponse update(Long perfId, PerformanceUpdateRequest request);

    void delete(Long perfId);
}
