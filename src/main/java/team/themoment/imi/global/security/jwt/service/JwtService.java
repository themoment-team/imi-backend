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
        Date expirationDate = new Date(System.currentTimeMillis() + accessTokenExpiration);
        String token = Jwts.builder()
                .claim("sub", userId)
                .claim("iat", System.currentTimeMillis() / 1000)
                .claim("exp", expirationDate.getTime() / 1000)
                .claim("jti", UUID.randomUUID().toString())
                .signWith(key)
                .compact();
        return new TokenDto(token, expirationDate.getTime());
    }

    public String issueRefreshToken(String userId) {
        Date expirationDate = new Date(System.currentTimeMillis() + refreshTokenExpiration);
        String token = Jwts.builder()
                .claim("sub", userId)
                .claim("iat", System.currentTimeMillis() / 1000)
                .claim("exp", expirationDate.getTime() / 1000)
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
        return parseClaims(token).getSubject();
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
        return Jwts.parser().verifyWith(key).clock(Date::new).build().parseSignedClaims(token).getPayload();
    }
}