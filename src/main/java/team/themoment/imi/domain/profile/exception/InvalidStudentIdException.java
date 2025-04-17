package team.themoment.imi.domain.profile.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class InvalidStudentIdException extends GlobalException {
    public InvalidStudentIdException() {
        super("학번 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
    }
}