package team.themoment.imi.domain.auth.data.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import team.themoment.imi.global.security.jwt.data.TokenDto;

public record LoginResDto(
        String accessToken,
        String refreshToken,
        @JsonAlias("exp")
        Long expiresIn,
        @JsonAlias("iat")
        Long issuedAt
) {
}