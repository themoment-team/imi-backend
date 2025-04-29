package team.themoment.imi.domain.user.data.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserReqDto(
        @NotBlank(message = "이름을 입력해주세요.")
        String name,
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,
        @NotNull(message = "학번을 입력해주세요.")
        @Min(value = 1101, message = "학번을 다시 확인해주세요.")
        @Max(value = 3418, message = "학번을 다시 확인해주세요.")
        Integer studentId,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {
}