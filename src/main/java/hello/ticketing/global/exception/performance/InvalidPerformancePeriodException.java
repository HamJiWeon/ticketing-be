package hello.ticketing.global.exception.performance;

import hello.ticketing.global.exception.TicketingException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

public class InvalidPerformancePeriodException extends TicketingException {

    public InvalidPerformancePeriodException(LocalDate startAt) {
        super(HttpStatus.BAD_REQUEST, "공연 종료일은 시작일보다 빠를 수 없습니다. 공연 시작일: " +startAt);
    }
}
