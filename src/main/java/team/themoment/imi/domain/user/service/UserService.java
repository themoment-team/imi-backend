package team.themoment.imi.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.themoment.imi.domain.profile.entity.Profile;
import team.themoment.imi.domain.user.dto.CreateUserReqDto;
import team.themoment.imi.domain.user.dto.UpdatePasswordReqDto;
import team.themoment.imi.domain.user.dto.UpdateUserReqDto;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.domain.user.repository.UserRepository;
import team.themoment.imi.global.exception.GlobalException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void join(CreateUserReqDto dto) {

        if(checkEmail(dto.getEmail())) {
            throw new GlobalException("이미 존재하는 이메일입니다.",HttpStatus.CONFLICT);
        }
        if(userRepository.existsByStudentId(dto.getStudentId())) {
            throw new GlobalException("이미 존재하는 학번입니다.", HttpStatus.CONFLICT);
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .studentId(dto.getStudentId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .profile(Profile.builder()
                        .wanted(List.of())
                        .major("")
                        .content("아직 자소서를 작성하지 않았습니다.")
                        .build())
                .build();
        userRepository.save(user);
    }

    private boolean isEmailFormat(String email){
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }
    public void updateUserInfo(User user,UpdateUserReqDto dto){
        if(!isEmailFormat(dto.getEmail())){
            throw new GlobalException("이메일 형식이 올바르지 않습니다.",HttpStatus.BAD_REQUEST);
        }
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setStudentId(dto.getStudentId());
        userRepository.save(user);
    }

    public void updatePassword(User user, UpdatePasswordReqDto dto){
        if(!passwordEncoder.matches(dto.getOldPassword(),user.getPassword())){
            throw new GlobalException("기존 비밀번호가 일치하지 않습니다.",HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }
}
