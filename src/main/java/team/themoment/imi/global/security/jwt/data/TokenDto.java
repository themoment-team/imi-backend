package team.themoment.imi.global.security.jwt.data;

public record TokenDto(
        String token,
        Long expiresIn
) {
}