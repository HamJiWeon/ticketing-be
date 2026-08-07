package hello.ticketing.global.exception.performance;

import hello.ticketing.global.exception.TicketingException;
import org.springframework.http.HttpStatus;

public class PerformanceNotFoundException extends TicketingException {

    public PerformanceNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 공연입니다. id: "+id);
    }
}
