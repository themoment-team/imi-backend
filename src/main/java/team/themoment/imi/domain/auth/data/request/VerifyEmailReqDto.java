package team.themoment.imi.domain.auth.data.request;

import jakarta.validation.constraints.NotNull;

public record VerifyEmailReqDto(
        @NotNull int authCode
) {
}