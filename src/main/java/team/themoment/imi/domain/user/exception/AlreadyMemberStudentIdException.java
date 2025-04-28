package team.themoment.imi.domain.user.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class AlreadyMemberStudentIdException extends GlobalException {
    public AlreadyMemberStudentIdException() {
        super("이미 가입된 학번입니다.", HttpStatus.CONFLICT);
    }
}