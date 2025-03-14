package team.themoment.imi.domain.auth.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class SignInFailedException extends GlobalException {
    public SignInFailedException() {
        super("Email or password is incorrect", HttpStatus.UNAUTHORIZED);
    }
}