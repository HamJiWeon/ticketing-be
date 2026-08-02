package hello.ticketing.controller;

import hello.ticketing.dto.request.PerformanceCreateRequest;
import hello.ticketing.dto.response.PerformanceDto;
import hello.ticketing.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/perf")
public class PerformanceController {

    private final PerformanceService performanceService;

    @PostMapping
    public ResponseEntity<PerformanceDto> create(@RequestBody PerformanceCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(performanceService.create(request));
    }
}
