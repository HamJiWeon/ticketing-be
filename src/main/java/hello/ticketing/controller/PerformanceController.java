package hello.ticketing.controller;

import hello.ticketing.dto.request.PerformanceCreateRequest;
import hello.ticketing.dto.request.PerformanceUpdateRequest;
import hello.ticketing.dto.response.PerformanceResponse;
import hello.ticketing.service.PerformanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<PerformanceResponse>> findAll(@RequestParam(required = false) String keyword,
                                                             @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(performanceService.findAll(keyword, pageable));
    }

    @PatchMapping("/{perfId}")
    public ResponseEntity<PerformanceResponse> update(@PathVariable Long perfId,
                                                      @Valid @RequestBody PerformanceUpdateRequest request) {
        return ResponseEntity.ok(performanceService.update(perfId, request));
    }

    @DeleteMapping("/{perfId}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long perfId) {
        performanceService.delete(perfId);
        return ResponseEntity.noContent().build();
    }
}
