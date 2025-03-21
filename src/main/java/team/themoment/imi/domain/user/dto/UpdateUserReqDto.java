package team.themoment.imi.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserReqDto {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
    @NotBlank(message = "학번을 입력해주세요.")
    private int studentId;
}
