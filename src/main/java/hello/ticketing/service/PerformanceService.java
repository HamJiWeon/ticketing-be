package hello.ticketing.service;

import hello.ticketing.dto.request.PerformanceCreateRequest;
import hello.ticketing.dto.response.PerformanceResponse;

public interface PerformanceService {

    PerformanceResponse create(PerformanceCreateRequest request);

    PerformanceResponse findById(Long perfId);
}
