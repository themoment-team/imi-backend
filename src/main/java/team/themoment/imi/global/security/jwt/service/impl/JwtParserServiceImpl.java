package team.themoment.imi.global.security.jwt.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.themoment.imi.global.security.exception.ExpiredAccessTokenException;
import team.themoment.imi.global.security.exception.InvalidAccessTokenException;
import team.themoment.imi.global.security.jwt.repository.RefreshTokenRedisRepository;
import team.themoment.imi.global.security.jwt.service.JwtParserService;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtParserServiceImpl implements JwtParserService {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final SecretKey key;

    public JwtParserServiceImpl(RefreshTokenRedisRepository refreshTokenRedisRepository, @Value("${spring.security.jwt.secret}") String secretKey) {
        this.refreshTokenRedisRepository = refreshTokenRedisRepository;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Override
    public String extractUserId(String token) {
        return parseClaims(token).getSubject();
    }

    @Override
    public Boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new ExpiredAccessTokenException();
        } catch (JwtException e) {
            throw new InvalidAccessTokenException();
        }
    }

    @Override
    public Boolean validateRefreshToken(String token) {
        return refreshTokenRedisRepository.existsById(token);
    }

    @Override
    public void deleteRefreshToken(String token) {
        refreshTokenRedisRepository.deleteById(token);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser().verifyWith(key).clock(Date::new).build().parseSignedClaims(token).getPayload();
    }
}