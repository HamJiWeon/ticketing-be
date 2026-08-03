package hello.ticketing.dto.request;

public record UserCreateRequest(
        String name,
        String password,
        String email,
        String phone,
        int age,
        String address
) {
}