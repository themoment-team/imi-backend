package team.themoment.imi.global.email.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@RedisHash(value = "auth_code")
@Getter
@Builder
public class AuthCode {
    @Id
    private String email;
    @Indexed
    private String authCode;
    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long expiration;
}