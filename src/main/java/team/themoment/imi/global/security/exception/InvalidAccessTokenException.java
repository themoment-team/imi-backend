package team.themoment.imi.global.security.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class InvalidAccessTokenException extends GlobalException {
    public InvalidAccessTokenException() {
        super("Invalid JWT Token", HttpStatus.UNAUTHORIZED);
    }
}