package team.themoment.imi.domain.user.data.request;

import jakarta.validation.constraints.*;
import team.themoment.imi.global.utils.ValidStudentId;

public record CreateUserReqDto(
        @NotBlank(message = "이름을 입력해주세요.")
        String name,
        @NotBlank(message = "이메일을 입력해주세요.")
        @Pattern(regexp = "^s\\d{5}@gsm\\.hs\\.kr$"
                , message = "이메일 형식이 올바르지 않습니다.")
        String email,
        @NotNull(message = "학번을 입력해주세요.")
        @ValidStudentId(message = "유효하지 않은 학번입니다.")
        Integer studentId,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {
}