package hello.ticketing.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
        String password,

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이어야 합니다.")
        String email,

        @NotBlank(message = "전화번호는 필수입니다.")
        String phone,

        @Min(value = 8, message = "나이는 8세 이상이어야 합니다.")
        int age,

        @NotBlank(message = "주소는 필수입니다.")
        String address
) {
}
