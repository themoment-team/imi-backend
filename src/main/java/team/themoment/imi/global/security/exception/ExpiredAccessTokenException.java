package team.themoment.imi.global.security.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class ExpiredAccessTokenException extends GlobalException {
    public ExpiredAccessTokenException() {
        super("만료된 액세스 토큰입니다. 다시 로그인해주세요.", HttpStatus.UNAUTHORIZED);
    }
}