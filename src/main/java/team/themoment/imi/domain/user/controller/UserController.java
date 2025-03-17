package team.themoment.imi.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.themoment.imi.domain.user.dto.CreateUserReqDto;
import team.themoment.imi.domain.user.dto.UpdatePasswordReqDto;
import team.themoment.imi.domain.user.dto.UpdateUserReqDto;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.domain.user.service.UserService;
import team.themoment.imi.global.utils.UserUtilNotImpl;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final UserUtilNotImpl userUtil;

    @PostMapping("/join")
    public void join(@Valid @RequestBody CreateUserReqDto dto) {
        userService.join(dto);
    }

    @PutMapping
    public void updateUserInfo(@Valid @RequestBody UpdateUserReqDto dto) {
        User user = userUtil.getCurrentUser();
        userService.updateUserInfo(user, dto);
    }
    @PutMapping("/password")
    public void updatePassword(@Valid @RequestBody UpdatePasswordReqDto dto) {
        User user = userUtil.getCurrentUser();
        userService.updatePassword(user, dto);
    }
}
