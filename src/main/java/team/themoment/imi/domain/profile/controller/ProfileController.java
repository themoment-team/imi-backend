package team.themoment.imi.domain.profile.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.imi.domain.profile.data.request.UpdateProfileReqDto;
import team.themoment.imi.domain.profile.data.response.ProfileListResDto;
import team.themoment.imi.domain.profile.data.response.ProfileResDto;
import team.themoment.imi.domain.profile.service.ProfileService;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    //studentInfo: 학번+이름
    @GetMapping("/{studentInfo}")
    public ResponseEntity<ProfileResDto> getProfile(@PathVariable String studentInfo){
        return ResponseEntity.ok(profileService.getProfileByStudentInfo(studentInfo));
    }

    @GetMapping("/my")
    public ResponseEntity<ProfileResDto> getMyProfile(){
        return ResponseEntity.status(HttpStatus.OK).body(profileService.getCurrentUserProfile());
    }

    @GetMapping("/list")
    public ResponseEntity<ProfileListResDto> getProfileList(){
        return ResponseEntity.status(HttpStatus.OK).body(profileService.getProfileList());
    }

    @PutMapping
    public ResponseEntity<Void> updateProfile(@Valid @RequestBody UpdateProfileReqDto dto) {
        profileService.updateProfile(dto.major(), dto.content(), dto.wanted(), dto.isLinked());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}