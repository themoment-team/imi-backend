package team.themoment.imi.domain.auth.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class AuthCodeExpiredException extends GlobalException {
    public AuthCodeExpiredException() {
        super("인증 코드가 만료되었습니다.", HttpStatus.UNAUTHORIZED);
    }
}