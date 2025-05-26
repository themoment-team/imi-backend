package team.themoment.imi.domain.user.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class EmailNotVerifiedException extends GlobalException {
    public EmailNotVerifiedException() {
        super("이메일 인증이 완료되지 않았습니다.", HttpStatus.FORBIDDEN);
    }
}