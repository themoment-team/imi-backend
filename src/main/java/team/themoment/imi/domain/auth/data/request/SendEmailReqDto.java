package team.themoment.imi.domain.auth.data.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SendEmailReqDto(
        @NotBlank(message = "이메일은 필수입니다.")
        @Pattern(regexp = "^s\\d{5}@gsm\\.hs\\.kr$",
                message = "유효한 이메일 형식이 아닙니다.")
        String email
) {
}