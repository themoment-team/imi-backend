package team.themoment.imi.global.log;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
@Component
public class GlobalLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = UUID.randomUUID().toString();
        request.setAttribute("traceId", traceId);

        String ip = request.getRemoteAddr();
        String sessionId = request.getRequestedSessionId();
        String method = request.getMethod();
        String uri = request.getRequestURI();

        log.info("Request [{}] Method: {}, IP: {}, Session: {}, URI: {}",
                traceId, method, ip, sessionId, uri);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String traceId = (String) request.getAttribute("traceId");

        String method = request.getMethod();
        String uri = request.getRequestURI();
        int status = response.getStatus();

        log.info("Response [{}] Method: {}, URI: {}, Status: {}",
                traceId, method, uri, status);

        if (ex != null) {
            log.error("Exception [{}] at URI: {}, Message: {}", traceId, uri, ex.getMessage(), ex);
        }
    }
}