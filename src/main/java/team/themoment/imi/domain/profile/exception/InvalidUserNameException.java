package team.themoment.imi.domain.profile.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class InvalidUserNameException extends GlobalException {
    public InvalidUserNameException() {
        super("이름이 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
    }
}