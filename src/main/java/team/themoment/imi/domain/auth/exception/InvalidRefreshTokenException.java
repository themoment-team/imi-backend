package team.themoment.imi.domain.auth.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class InvalidRefreshTokenException extends GlobalException {
    public InvalidRefreshTokenException() {
        super("Invalid refresh token", HttpStatus.UNAUTHORIZED);
    }
}