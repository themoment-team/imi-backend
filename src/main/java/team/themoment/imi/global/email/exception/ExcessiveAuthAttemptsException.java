package team.themoment.imi.global.email.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class ExcessiveAuthAttemptsException extends GlobalException {
    public ExcessiveAuthAttemptsException() {
        super("이메일 인증 시도가 너무 많습니다. 잠시 후 다시 시도해주세요.", HttpStatus.TOO_MANY_REQUESTS);
    }
}