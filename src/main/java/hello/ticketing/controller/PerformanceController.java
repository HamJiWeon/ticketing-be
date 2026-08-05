package hello.ticketing.controller;

import hello.ticketing.dto.request.PerformanceCreateRequest;
import hello.ticketing.dto.response.PerformanceResponse;
import hello.ticketing.service.PerformanceService;
import jakarta.validation.Valid;
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
    public ResponseEntity<PerformanceResponse> create(@Valid @RequestBody PerformanceCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(performanceService.create(request));
    }

    @GetMapping("/{perfId}")
    public ResponseEntity<PerformanceResponse> findById(@PathVariable Long perfId) {
        return ResponseEntity.ok(performanceService.findById(perfId));
    }
}
