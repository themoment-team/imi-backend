package team.themoment.imi.global.email.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class EmailSendingFailedException extends GlobalException {
    public EmailSendingFailedException() {
        super("이메일 전송에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}