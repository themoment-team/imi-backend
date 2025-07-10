package team.themoment.imi.global.security.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.themoment.imi.global.security.exception.ExpiredAccessTokenException;
import team.themoment.imi.global.security.exception.InvalidAccessTokenException;
import team.themoment.imi.global.security.jwt.data.TokenDto;
import team.themoment.imi.global.security.jwt.entity.RefreshToken;
import team.themoment.imi.global.security.jwt.repository.RefreshTokenRedisRepository;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final SecretKey key;
    @Value("${spring.security.jwt.access-token-expiration}")
    private Long accessTokenExpiration;
    @Value("${spring.security.jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    public JwtService(RefreshTokenRedisRepository refreshTokenRedisRepository, @Value("${spring.security.jwt.secret}") String secretKey) {
        this.refreshTokenRedisRepository = refreshTokenRedisRepository;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public TokenDto issueAccessToken(String userId) {
        long currentTimeMillis = System.currentTimeMillis();
        long currentTimeSeconds = currentTimeMillis / 1000;
        Date expirationDate = new Date(currentTimeMillis + (accessTokenExpiration * 1000L));
        long expirationSeconds = expirationDate.getTime() / 1000;
        String token = Jwts.builder()
                .claim("sub", userId)
                .claim("iat", currentTimeSeconds)
                .claim("exp", expirationSeconds)
                .claim("jti", UUID.randomUUID().toString())
                .signWith(key)
                .compact();
        return new TokenDto(token, expirationDate.getTime());
    }

    public String issueRefreshToken(String userId) {
        long currentTimeMillis = System.currentTimeMillis();
        long currentTimeSeconds = currentTimeMillis / 1000;
        Date expirationDate = new Date(currentTimeMillis + (refreshTokenExpiration * 1000L));
        long expirationSeconds = expirationDate.getTime() / 1000;
        String token = Jwts.builder()
                .claim("sub", userId)
                .claim("iat", currentTimeSeconds)
                .claim("exp", expirationSeconds)
                .claim("jti", UUID.randomUUID().toString())
                .signWith(key)
                .compact();
        refreshTokenRedisRepository.save(RefreshToken.builder()
                .refreshToken(token)
                .userId(userId)
                .expiration(expirationDate.getTime())
                .build()
        );
        return token;
    }

    public String extractUserId(String token) {
        try {
            return parseClaims(token).getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

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

    public Boolean validateRefreshToken(String token) {
        return refreshTokenRedisRepository.existsById(token);
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRedisRepository.deleteById(token);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .clockSkewSeconds(60)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}