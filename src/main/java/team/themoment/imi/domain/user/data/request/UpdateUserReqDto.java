package team.themoment.imi.domain.user.data.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateUserReqDto(
        @NotBlank(message = "이름을 입력해주세요.")
        String name,
        @NotBlank(message = "이메일을 입력해주세요.")
        @Pattern(regexp = "^s\\d{5}@(gsm\\.hs\\.kr|gsmhs\\.kr)$",
                message = "이메일 형식이 올바르지 않습니다.")
        String email,
        @NotBlank(message = "학번을 입력해주세요.")
        Integer studentId
) {
}