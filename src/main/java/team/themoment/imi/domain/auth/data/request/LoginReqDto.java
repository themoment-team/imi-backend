package team.themoment.imi.domain.auth.data.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginReqDto(
        @NotBlank @Email @Size(max = 16, min = 16) String email,
        @NotBlank @Size(max = 16, min = 8) String password
) {
}