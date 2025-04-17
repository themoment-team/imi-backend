package team.themoment.imi.domain.profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.themoment.imi.domain.profile.data.response.ProfileListResDto;
import team.themoment.imi.domain.profile.data.response.ProfileResDto;
import team.themoment.imi.domain.profile.entity.Profile;
import team.themoment.imi.domain.profile.exception.InvalidStudentIdException;
import team.themoment.imi.domain.profile.repository.ProfileJpaRepository;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.domain.profile.exception.InvalidUserNameException;
import team.themoment.imi.domain.user.exception.MemberNotFoundException;
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
                    .orElseThrow(MemberNotFoundException::new);

            if (!user.getName().equals(name)) throw new InvalidUserNameException();

            return profileMapper.toProfileResDto(user.getProfile());
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new InvalidStudentIdException();
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