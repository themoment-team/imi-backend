package team.themoment.imi.domain.auth.data.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendEmailReqDto(
        @NotBlank @Email String email
) {
}