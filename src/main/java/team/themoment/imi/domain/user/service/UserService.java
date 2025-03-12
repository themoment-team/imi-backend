package team.themoment.imi.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import team.themoment.imi.domain.profile.entity.Profile;
import team.themoment.imi.domain.user.dto.CreateUserReqDto;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.domain.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void join(CreateUserReqDto dto) {

        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if(userRepository.existsByStudentId(dto.getStudentId())) {
            throw new IllegalArgumentException("이미 존재하는 학번입니다.");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .studentId(dto.getStudentId())
                .password(dto.getPassword())
                .profile(Profile.builder()
                        .wanted(List.of())
                        .major("")
                        .content("")
                        .build())
                .build();
        userRepository.save(user);
    }}
