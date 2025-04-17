package team.themoment.imi.domain.user.data.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserReqDto(
        @NotBlank(message = "이름을 입력해주세요.")
        String name,
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,
        @NotBlank(message = "학번을 입력해주세요.")
        Integer studentId
) {
}