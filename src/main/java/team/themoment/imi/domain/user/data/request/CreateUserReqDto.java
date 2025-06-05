package team.themoment.imi.domain.user.data.request;

import jakarta.validation.constraints.*;

public record CreateUserReqDto(
        @NotBlank(message = "이름을 입력해주세요.")
        String name,
        @NotBlank(message = "이메일을 입력해주세요.")
        @Pattern(regexp = "^s\\d{5}@(gsm\\.hs\\.kr|gsmhs\\.kr)$"
                , message = "이메일 형식이 올바르지 않습니다.")
        String email,
        @NotNull(message = "학번을 입력해주세요.")
        @Min(value = 1101, message = "학번을 다시 확인해주세요.")
        @Max(value = 3418, message = "학번을 다시 확인해주세요.")
        Integer studentId,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {
}