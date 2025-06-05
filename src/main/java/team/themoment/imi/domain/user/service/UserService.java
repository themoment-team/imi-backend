package team.themoment.imi.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.themoment.imi.domain.profile.entity.Profile;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.domain.user.exception.*;
import team.themoment.imi.domain.user.repository.UserJpaRepository;
import team.themoment.imi.global.email.entity.Authentication;
import team.themoment.imi.global.email.repository.AuthenticationRedisRepository;
import team.themoment.imi.global.utils.UserUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserUtil userUtil;
    private final AuthenticationRedisRepository authenticationRedisRepository;

    public void join(String name, String email, int studentId, String password) {
        if (userJpaRepository.existsByEmail(email)) {
            throw new AlreadyMemberEmailException();
        }
        if (userJpaRepository.existsByStudentId(studentId)) {
            throw new AlreadyMemberStudentIdException();
        }
        Authentication authentication = authenticationRedisRepository.findById(email)
                .orElseThrow(EmailNotVerifiedException::new);
        if (!authentication.isVerified()) {
            throw new EmailNotVerifiedException();
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

    public void updateUserInfo(String name, String email, int studentId) {
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

    public void updatePassword(String email, String newPassword) {
        User user = userUtil.getCurrentUser();
        if (authenticationRedisRepository.findById(email)
                .orElseThrow(EmailNotVerifiedException::new).isVerified()) {
            throw new EmailNotVerifiedException();
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userJpaRepository.save(user);
    }

    public Boolean checkEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
}