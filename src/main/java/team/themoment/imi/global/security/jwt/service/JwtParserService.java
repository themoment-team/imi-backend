package team.themoment.imi.global.security.jwt.service;

public interface JwtParserService {
    String extractUserId(String token);

    Boolean validateToken(String token);

    Boolean validateRefreshToken(String token);
}