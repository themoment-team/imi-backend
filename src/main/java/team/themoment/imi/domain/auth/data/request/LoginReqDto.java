package team.themoment.imi.domain.auth.data.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginReqDto(
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        @Size(max = 16, min = 16, message = "이메일은 16자리여야 합니다.")
        String email,
        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(max = 16, min = 8, message = "비밀번호는 8자 이상 16자 이하여야 합니다")
        String password
) {
}