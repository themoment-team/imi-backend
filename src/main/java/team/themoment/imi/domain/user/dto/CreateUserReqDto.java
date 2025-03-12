package team.themoment.imi.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserReqDto {
    private String name;
    private String email;
    private int studentId;
    private String password;
}
