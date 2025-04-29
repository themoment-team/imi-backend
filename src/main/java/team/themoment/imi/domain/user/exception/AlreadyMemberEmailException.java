package team.themoment.imi.domain.user.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class AlreadyMemberEmailException extends GlobalException {
    public AlreadyMemberEmailException() {
        super("이미 가입된 이메일입니다.", HttpStatus.CONFLICT);
    }
}