package team.themoment.imi.global.security.jwt.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@RedisHash(value = "refresh_token")
@Builder
@Getter
public class RefreshToken {
    @Id
    private String refreshToken;
    @Indexed
    private String userId;
    @TimeToLive(unit = TimeUnit.SECONDS)
    private long expiration;
}