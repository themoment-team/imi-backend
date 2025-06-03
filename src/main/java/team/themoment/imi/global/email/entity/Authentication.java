package team.themoment.imi.global.email.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@RedisHash(value = "authentication")
@Builder
@Getter
public class Authentication {
    @Id
    private String email;
    private int sendAttempt;
    private int verificationAttempt;
    private boolean verified;
    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long expiration;
}