package team.themoment.imi.domain.profile.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class FirstGradeRequiredException extends GlobalException {
    public FirstGradeRequiredException() {
        super("1학년만 사용할 수 있는 기능입니다.", HttpStatus.BAD_REQUEST);
    }
}