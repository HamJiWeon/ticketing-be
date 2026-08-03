package hello.ticketing.dto.request;

public record UserUpdateRequest(
        String password,
        String phone,
        String address
) {
}