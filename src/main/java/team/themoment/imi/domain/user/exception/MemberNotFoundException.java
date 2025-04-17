package team.themoment.imi.domain.user.exception;

import org.springframework.http.HttpStatus;
import team.themoment.imi.global.exception.GlobalException;

public class MemberNotFoundException extends GlobalException {
    public MemberNotFoundException() {
        super("존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND);
    }
}