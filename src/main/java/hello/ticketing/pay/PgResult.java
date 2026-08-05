package hello.ticketing.pay;

import java.time.LocalDateTime;

public record PgResult(
        boolean success,
        String pgTransactionKey,
        LocalDateTime processedAt,
        String failCode,
        String failMessage
) {
    public static PgResult success(String pgTransactionKey, LocalDateTime processedAt) {
        return new PgResult(
                true,
                pgTransactionKey,
                processedAt,
                null,
                null
        );
    }

    public static PgResult failure(String failCode, String failMessage) {
        return new PgResult(
                false,
                null,
                LocalDateTime.now(),
                failCode,
                failMessage
        );
    }
}