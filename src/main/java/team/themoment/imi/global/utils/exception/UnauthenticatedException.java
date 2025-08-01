package team.themoment.imi.global.utils.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class UnauthenticatedException extends GlobalException {
    public UnauthenticatedException() {
        super("인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED);
    }
}