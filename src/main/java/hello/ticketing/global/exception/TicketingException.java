package hello.ticketing.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class TicketingException extends RuntimeException {

    private final HttpStatus httpStatus;

    public TicketingException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
