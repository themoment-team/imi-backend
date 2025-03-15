package team.themoment.imi.global.security.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class InvalidJwtTokenException extends GlobalException {
    public InvalidJwtTokenException() {
        super("Invalid JWT Token", HttpStatus.UNAUTHORIZED);
    }
}