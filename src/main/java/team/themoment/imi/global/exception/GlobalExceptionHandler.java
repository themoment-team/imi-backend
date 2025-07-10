package team.themoment.imi.global.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableWebMvc
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    private ResponseEntity<ErrorResponse> expectedException(GlobalException ex) {
        log.warn("ExpectedException : {} ", ex.getMessage());
        log.trace("ExpectedException Details : ", ex);
        return ResponseEntity.status(ex.getHttpStatus().value()).body(ErrorResponse.of(ex));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> expiredJwtException(ExpiredJwtException ex) {
        log.warn("JWT Token Expired: {} ", ex.getMessage());
        log.trace("JWT Token Expired Details : ", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
                .body(new ErrorResponse("토큰이 만료되었습니다. 다시 로그인해주세요."));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> unExpectedException(RuntimeException ex) {
        log.error("UnExpectedException Occur : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(new ErrorResponse("internal server error has occurred"));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> noHandlerFoundException(NoHandlerFoundException ex) {
        log.warn("Not Found Endpoint : {}", ex.getMessage());
        log.trace("Not Found Endpoint Details : ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn("HttpMessageNotReadableException : {}", ex.getMessage());
        log.trace("HttpMessageNotReadableException Details : ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("UnexpectedException Occur : ", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT.value())
                .body(new ErrorResponse("Data integrity violation has occurred")
                );
    }
}