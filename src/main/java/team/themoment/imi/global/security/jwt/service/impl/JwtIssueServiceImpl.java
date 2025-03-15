package team.themoment.imi.global.security.jwt.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.themoment.imi.global.security.jwt.data.TokenDto;
import team.themoment.imi.global.security.jwt.entity.RefreshTokenRedisEntity;
import team.themoment.imi.global.security.jwt.repository.RefreshTokenRedisRepository;
import team.themoment.imi.global.security.jwt.service.JwtIssueService;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtIssueServiceImpl implements JwtIssueService {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    private final SecretKey key;

    @Value("${spring.security.jwt.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${spring.security.jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    public JwtIssueServiceImpl(RefreshTokenRedisRepository refreshTokenRedisRepository, @Value("${spring.security.jwt.secret}") String secretKey) {
        this.refreshTokenRedisRepository = refreshTokenRedisRepository;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Override
    public TokenDto issueAccessToken(String userId) {
        Date expirationDate = new Date(System.currentTimeMillis() + accessTokenExpiration * 1000);
        String token = Jwts.builder()
                .claim("sub", userId)
                .claim("iat", System.currentTimeMillis() / 1000)
                .claim("exp", expirationDate.getTime() / 1000)
                .claim("jti", UUID.randomUUID().toString())
                .signWith(key)
                .compact();
        return new TokenDto(token, expirationDate.getTime());
    }

    @Override
    public String issueRefreshToken(String userId) {
        Date expirationDate = new Date(System.currentTimeMillis() + refreshTokenExpiration * 1000);
        String token = Jwts.builder()
                .claim("sub", userId)
                .claim("iat", System.currentTimeMillis() / 1000)
                .claim("exp", expirationDate.getTime() / 1000)
                .claim("jti", UUID.randomUUID().toString())
                .signWith(key)
                .compact();
        refreshTokenRedisRepository.save(RefreshTokenRedisEntity.builder()
                .refreshToken(token)
                .userId(userId)
                .expiration(expirationDate.getTime())
                .build()
        );
        return token;
    }
}