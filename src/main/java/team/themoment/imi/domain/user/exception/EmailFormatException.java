package team.themoment.imi.domain.user.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class EmailFormatException extends GlobalException {
    public EmailFormatException() {
        super("이메일 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
    }
}