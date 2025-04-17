package team.themoment.imi.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.themoment.imi.domain.profile.entity.Profile;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.domain.user.repository.UserJpaRepository;
import team.themoment.imi.global.exception.GlobalException;
import team.themoment.imi.global.utils.UserUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserUtil userUtil;

    public void join(String name, String email, int studentId, String password) {
        if (userJpaRepository.existsByEmail(email)) {
            throw new GlobalException("이미 존재하는 이메일입니다.", HttpStatus.CONFLICT);
        }
        if (userJpaRepository.existsByStudentId(studentId)) {
            throw new GlobalException("이미 존재하는 학번입니다.", HttpStatus.CONFLICT);
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
        return !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    public void updateUserInfo(String name, String email, int studentId) {
        if (isEmailFormat(email)) {
            throw new GlobalException("이메일 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
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
            throw new GlobalException("기존 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userJpaRepository.save(user);
    }

    public Boolean checkEmail(String email) {
        if (isEmailFormat(email)) {
            throw new GlobalException("이메일 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        return userJpaRepository.existsByEmail(email);
    }
}