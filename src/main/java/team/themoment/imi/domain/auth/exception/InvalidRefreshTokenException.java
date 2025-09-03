package team.themoment.imi.domain.auth.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class InvalidRefreshTokenException extends GlobalException {
    public InvalidRefreshTokenException() {
        super("유효하지 않은 리프레시 토큰입니다.", HttpStatus.UNAUTHORIZED);
    }
}