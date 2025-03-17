package team.themoment.imi.domain.profile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team.themoment.imi.domain.profile.dto.ProfileListResDto;
import team.themoment.imi.domain.profile.dto.ProfileResDto;
import team.themoment.imi.domain.profile.dto.UpdateProfileReqDto;
import team.themoment.imi.domain.profile.entity.Profile;
import team.themoment.imi.domain.profile.service.ProfileService;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.global.mapper.ProfileMapper;
import team.themoment.imi.global.exception.GlobalException;
import team.themoment.imi.global.utils.UserUtil;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileMapper profileMapper;
    private final UserUtil userUtil;

    //studentInfo: 학번+이름
    @GetMapping("/{studentInfo}")
    public ProfileResDto getProfile(@PathVariable String studentInfo){
        try {
            String name = studentInfo.substring(4);
            int studentId = Integer.parseInt(studentInfo.substring(0,4));
            Profile profile = profileService.getProfileByStudentInfo(studentId,name);
            return profileMapper.toProfileResDto(profile);

        }catch (IndexOutOfBoundsException e){
            throw new GlobalException("너무 짧은 학생정보가 입력되었습니다.", HttpStatus.BAD_REQUEST);
        }catch (NumberFormatException e){
            throw new GlobalException("학번이 잘못 입력되었습니다.", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/my")
    public ProfileResDto getMyProfile(){
        User user = userUtil.getCurrentUser();
        return profileMapper.toProfileResDto(user.getProfile());
    }
    @GetMapping("/list")
    public ProfileListResDto getProfileList(){
        return profileMapper.toProfileListResDto(profileService.getProfileList());
    }

    @PutMapping
    public void updateProfile(@RequestBody UpdateProfileReqDto dto) {
        User user = userUtil.getCurrentUser();
        profileService.updateProfile(user.getProfile(), dto);
    }
}
