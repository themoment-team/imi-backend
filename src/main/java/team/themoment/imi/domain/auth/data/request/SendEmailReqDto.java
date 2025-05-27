package team.themoment.imi.domain.auth.data.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SendEmailReqDto(
        @NotBlank
        @Pattern(regexp = "^s\\d{5}@(gsm\\.hs\\.kr|gsmhs\\.kr)$")
        String email
) {
}