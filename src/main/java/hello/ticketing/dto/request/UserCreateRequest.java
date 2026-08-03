package hello.ticketing.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotBlank
        String name,

        @NotBlank
        @Size(min = 8)
        String password,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String phone,

        int age,

        @NotBlank
        String address
) {
}
