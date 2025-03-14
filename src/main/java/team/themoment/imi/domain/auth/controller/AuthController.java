package team.themoment.imi.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.imi.domain.auth.data.request.LoginReqDto;
import team.themoment.imi.domain.auth.data.request.RefreshJwtReqDto;
import team.themoment.imi.domain.auth.data.response.LoginResDto;
import team.themoment.imi.domain.auth.service.LogInService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private LogInService logInService;

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@Valid @RequestBody LoginReqDto reqDto) {
        return ResponseEntity.status(HttpStatus.OK).body(logInService.execute(reqDto.email(), reqDto.password()));
    }

    @PostMapping("/refresh")
    public void refresh(@Valid @RequestBody RefreshJwtReqDto reqDto) {
        // authService.refresh();
    }
}