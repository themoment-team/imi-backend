package team.themoment.imi.domain.auth.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class InvalidAuthCodeException extends GlobalException {
    public InvalidAuthCodeException() {
        super("유효하지 않은 인증 코드입니다.", HttpStatus.UNAUTHORIZED);
    }
}