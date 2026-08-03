package hello.ticketing.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @NotBlank
        @Size(min = 8)
        String password,

        @NotBlank
        String phone,

        @NotBlank
        String address
) {
}
