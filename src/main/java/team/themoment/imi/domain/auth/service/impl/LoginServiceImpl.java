package team.themoment.imi.domain.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.themoment.imi.domain.auth.data.response.LoginResDto;
import team.themoment.imi.domain.auth.exception.SignInFailedException;
import team.themoment.imi.domain.auth.service.LogInService;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.domain.user.repository.UserJpaRepository;
import team.themoment.imi.global.security.jwt.data.TokenDto;
import team.themoment.imi.global.security.jwt.service.JwtIssueService;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LogInService {

    private final JwtIssueService jwtIssueService;
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResDto execute(String email, String password) {
        User user = userJpaRepository.findByEmail(email);
        if (passwordEncoder.matches(password, user.getPassword())) {
            TokenDto accessToken = jwtIssueService.issueAccessToken(user.getEmail());
            String refreshToken = jwtIssueService.issueRefreshToken(user.getEmail());
            return new LoginResDto(accessToken.token(), refreshToken, accessToken.expiresIn(), new Date().getTime());
        } else {
            throw new SignInFailedException();
        }
    }
}
