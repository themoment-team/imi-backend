package team.themoment.imi.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.themoment.imi.domain.profile.entity.Profile;
import team.themoment.imi.domain.profile.exception.FirstGradeRequiredException;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.domain.user.exception.AlreadyMemberException;
import team.themoment.imi.domain.user.exception.EmailFormatException;
import team.themoment.imi.domain.user.exception.InvalidPasswordException;
import team.themoment.imi.domain.user.repository.UserJpaRepository;
import team.themoment.imi.global.utils.UserUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserUtil userUtil;

    public void join(String name, String email, int studentId, String password) {
        if (studentId > 2000 || !email.startsWith("25", 1)) {
            throw new FirstGradeRequiredException();
        }
        if (userJpaRepository.existsByEmail(email)) {
            throw new AlreadyMemberException();
        }
        if (userJpaRepository.existsByStudentId(studentId)) {
            throw new AlreadyMemberException();
        }

        User user = User.builder()
                .name(name)
                .email(email)
                .studentId(studentId)
                .password(passwordEncoder.encode(password))
                .profile(Profile.builder()
                        .wanted(List.of())
                        .major("")
                        .content("아직 자소서를 작성하지 않았습니다.")
                        .build())
                .build();

        userJpaRepository.save(user);
    }

    private boolean isEmailFormat(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    public void updateUserInfo(String name, String email, int studentId) {
        if (isEmailFormat(email)) {
            throw new EmailFormatException();
        }

        User user = userUtil.getCurrentUser();

        userJpaRepository.save(
                User.builder()
                        .id(user.getId())
                        .name(name)
                        .profile(user.getProfile())
                        .email(email)
                        .studentId(studentId)
                        .build()
        );
    }

    public void updatePassword(String oldPassword, String newPassword) {
        User user = userUtil.getCurrentUser();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidPasswordException();
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userJpaRepository.save(user);
    }

    public Boolean checkEmail(String email) {
        if (isEmailFormat(email)) {
            throw new EmailFormatException();
        }
        return userJpaRepository.existsByEmail(email);
    }
}