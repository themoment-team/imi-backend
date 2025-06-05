package team.themoment.imi.domain.user.data.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdatePasswordReqDto(
        @NotBlank(message = "기존 비밀번호를 입력해주세요.")
        @Pattern(regexp = "^s\\d{5}@(gsm\\.hs\\.kr|gsmhs\\.kr)$",
                message = "유효한 이메일 형식이 아닙니다.")
        String email,
        @NotBlank(message = "공백없이 새 비밀번호를 입력해주세요.")
        String newPassword
) {
}