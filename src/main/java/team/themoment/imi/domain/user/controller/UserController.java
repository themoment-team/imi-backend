package team.themoment.imi.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.themoment.imi.domain.user.data.request.CheckEmailReqDto;
import team.themoment.imi.domain.user.data.request.CreateUserReqDto;
import team.themoment.imi.domain.user.data.request.UpdatePasswordReqDto;
import team.themoment.imi.domain.user.data.request.UpdateUserReqDto;
import team.themoment.imi.domain.user.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@Valid @RequestBody CreateUserReqDto dto) {
        userService.join(dto.name(), dto.email(), dto.studentId(), dto.password());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUserInfo(@Valid @RequestBody UpdateUserReqDto dto) {
        userService.updateUserInfo(dto.name(), dto.email(), dto.studentId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UpdatePasswordReqDto dto) {
        userService.updatePassword(dto.email(), dto.newPassword());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@Valid @RequestBody CheckEmailReqDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkEmail(dto.email()));
    }
}