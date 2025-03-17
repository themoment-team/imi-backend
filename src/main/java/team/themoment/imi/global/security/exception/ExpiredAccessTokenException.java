package team.themoment.imi.global.security.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class ExpiredAccessTokenException extends GlobalException {
    public ExpiredAccessTokenException() {
        super("JWT Token has expired", HttpStatus.UNAUTHORIZED);
    }
}