package team.themoment.imi.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.themoment.imi.domain.user.dto.CheckEmailReqDto;
import team.themoment.imi.domain.user.dto.CreateUserReqDto;
import team.themoment.imi.domain.user.dto.UpdatePasswordReqDto;
import team.themoment.imi.domain.user.dto.UpdateUserReqDto;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.domain.user.service.UserService;
import team.themoment.imi.global.utils.UserUtil;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final UserUtil userUtil;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@Valid @RequestBody CreateUserReqDto dto) {
        userService.join(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUserInfo(@Valid @RequestBody UpdateUserReqDto dto) {
        User user = userUtil.getCurrentUser();
        userService.updateUserInfo(user, dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UpdatePasswordReqDto dto) {
        User user = userUtil.getCurrentUser();
        userService.updatePassword(user, dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmail(@Valid @RequestBody CheckEmailReqDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkEmail(dto.getEmail()));
    }
}
