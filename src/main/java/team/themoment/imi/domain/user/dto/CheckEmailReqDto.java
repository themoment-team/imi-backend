package team.themoment.imi.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CheckEmailReqDto {
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;
}
