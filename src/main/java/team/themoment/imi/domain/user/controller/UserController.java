package team.themoment.imi.domain.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.themoment.imi.domain.profile.entity.Profile;
import team.themoment.imi.domain.user.dto.CreateUserReqDto;
import team.themoment.imi.domain.user.entity.User;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/join")
    public void join(@RequestBody CreateUserReqDto dto) {

    }
}
