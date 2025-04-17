package team.themoment.imi.domain.user.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class AlreadyMemberException extends GlobalException {
    public AlreadyMemberException() {
        super("이미 가입된 이메일입니다.", HttpStatus.CONFLICT);
    }
}