package hello.ticketing.pay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;

@Component
public class TossPaymentGateway {

    private static final Set<String> AUTH_ERROR_CODES = Set.of(
            "INVALID_CLIENT_KEY", "INVALID_API_KEY",
            "UNAUTHORIZED_KEY", "INCORRECT_BASIC_AUTH_FORMAT"
    );

    private final RestClient restClient;

    public TossPaymentGateway(@Value("${toss.secret-key}") String secretKey) {
        String encodedKey = Base64.getEncoder()
                .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));

        this.restClient = RestClient.builder()
                .baseUrl("https://api.tosspayments.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedKey)
                .build();
    }

    //public PgResult approve(String paymentKey, String orderId, int amount) {// POST /v1/payments/confirm}

    //public PgResult cancel(String paymentKey, String cancelReason) {// POST /v1/payments/{paymentKey}/cancel}

    private PgResult handleError(String errorCode, String errorMessage) {
        if (AUTH_ERROR_CODES.contains(errorCode)) {
            throw new IllegalStateException("토스 API 인증 설정이 잘못됐습니다: " + errorCode);
        }
        return PgResult.failure(errorCode, errorMessage);
    }
}
