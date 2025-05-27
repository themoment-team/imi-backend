package team.themoment.imi.global.email.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "authentication")
@Builder
@Getter
public class Authentication {
    @Id
    private String email;
    private int attempts;
    private boolean verified;
    @TimeToLive
    private Long expiration;
}