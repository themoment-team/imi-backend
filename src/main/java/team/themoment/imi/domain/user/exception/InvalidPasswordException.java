package team.themoment.imi.domain.user.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class InvalidPasswordException extends GlobalException {
    public InvalidPasswordException() {
        super("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
    }
}