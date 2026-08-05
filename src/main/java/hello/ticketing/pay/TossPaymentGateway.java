package hello.ticketing.pay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class TossPaymentGateway {

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
}
