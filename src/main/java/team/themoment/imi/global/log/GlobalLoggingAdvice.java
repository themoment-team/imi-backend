package team.themoment.imi.global.log;

import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.UUID;

@ControllerAdvice
@Slf4j
public class GlobalLoggingAdvice {

    @ModelAttribute
    public void logRequest(HttpServletRequest request) {
        UUID code = UUID.randomUUID();
        String ip = request.getRemoteAddr();
        String sessionId = request.getRequestedSessionId();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        log.info("Request: [Request: {}] IP: {}, Session: {}, URI: {}, Code: {}", method, ip, sessionId, uri, code);
    }
}