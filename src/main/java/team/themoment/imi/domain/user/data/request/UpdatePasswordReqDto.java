package team.themoment.imi.domain.user.data.request;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordReqDto(
        @NotBlank(message = "기존 비밀번호를 입력해주세요.")
        String oldPassword,
        @NotBlank(message = "새 비밀번호를 입력해주세요.")
        String newPassword
) {
}