package team.themoment.imi.domain.profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.imi.domain.profile.dto.UpdateProfileReqDto;
import team.themoment.imi.domain.profile.entity.Profile;
import team.themoment.imi.domain.profile.repository.ProfileRepository;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.domain.user.repository.UserRepository;
import team.themoment.imi.global.exception.GlobalException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public Profile getProfileByStudentInfo(int studentId,String name){
        User user = userRepository.getUserByStudentId(studentId)
                .orElseThrow(() -> new GlobalException("존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND));
        if(!user.getName().equals(name)) throw new GlobalException("이름이 일치하지 않습니다.",HttpStatus.NOT_FOUND);
        return user.getProfile();
    }
    public List<Profile> getProfileList(){
        return profileRepository.findAll();
    }
    public void updateProfile(Profile profile, UpdateProfileReqDto dto){
        profile.setWanted(dto.getWanted());
        profile.setMajor(dto.getMajor());
        profile.setContent(dto.getContent());
        profileRepository.save(profile);
    }
}
