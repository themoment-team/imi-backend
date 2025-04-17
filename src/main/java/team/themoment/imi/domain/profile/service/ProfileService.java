package team.themoment.imi.domain.profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.imi.domain.profile.data.response.ProfileListResDto;
import team.themoment.imi.domain.profile.data.response.ProfileResDto;
import team.themoment.imi.domain.profile.entity.Profile;
import team.themoment.imi.domain.profile.repository.ProfileJpaRepository;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.domain.user.repository.UserJpaRepository;
import team.themoment.imi.global.exception.GlobalException;
import team.themoment.imi.global.mapper.ProfileMapper;
import team.themoment.imi.global.utils.UserUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileJpaRepository profileJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ProfileMapper profileMapper;
    private final UserUtil userUtil;

    public ProfileResDto getCurrentUserProfile() {
        User user = userUtil.getCurrentUser();
        return profileMapper.toProfileResDto(user.getProfile());
    }

    public ProfileResDto getProfileByStudentInfo(String studentInfo) {
        try {
            String name = studentInfo.substring(4);
            int studentId = Integer.parseInt(studentInfo.substring(0, 4));

            User user = userJpaRepository.getUserByStudentId(studentId)
                    .orElseThrow(() -> new GlobalException("존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND));

            if (!user.getName().equals(name)) throw new GlobalException("이름이 일치하지 않습니다.", HttpStatus.NOT_FOUND);

            return profileMapper.toProfileResDto(user.getProfile());
        } catch (IndexOutOfBoundsException e) {
            throw new GlobalException("너무 짧은 학생정보가 입력되었습니다.", HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException e) {
            throw new GlobalException("학번이 잘못 입력되었습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public ProfileListResDto getProfileList() {
        return profileMapper.toProfileListResDto(profileJpaRepository.findAll());
    }

    public void updateProfile(String major, String content, List<String> wanted) {
        User user = userUtil.getCurrentUser();
        profileJpaRepository.save(
                Profile.builder()
                        .id(user.getId())
                        .wanted(wanted)
                        .major(major)
                        .content(content)
                        .build()
        );
    }
}