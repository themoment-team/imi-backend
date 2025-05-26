package team.themoment.imi.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.themoment.imi.domain.auth.data.request.LoginReqDto;
import team.themoment.imi.domain.auth.data.request.RefreshJwtReqDto;
import team.themoment.imi.domain.auth.data.request.SendEmailReqDto;
import team.themoment.imi.domain.auth.data.request.VerifyEmailReqDto;
import team.themoment.imi.domain.auth.data.response.LoginResDto;
import team.themoment.imi.domain.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@Valid @RequestBody LoginReqDto reqDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(reqDto.email(), reqDto.password()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResDto> refresh(@Valid @RequestBody RefreshJwtReqDto reqDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.refresh(reqDto.refreshToken()));
    }

    @PostMapping("/send-email")
    public ResponseEntity<Void> sendEmail(@Valid @RequestBody SendEmailReqDto reqDto) {
        authService.sendEmail(reqDto.email());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(@Valid @RequestBody VerifyEmailReqDto reqDto) {
        authService.verifyAuthCode(reqDto.authCode());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}