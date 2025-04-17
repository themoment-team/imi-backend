package team.themoment.imi.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.themoment.imi.domain.auth.data.response.LoginResDto;
import team.themoment.imi.domain.auth.exception.InvalidRefreshTokenException;
import team.themoment.imi.domain.auth.exception.SignInFailedException;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.domain.user.repository.UserJpaRepository;
import team.themoment.imi.global.security.jwt.data.TokenDto;
import team.themoment.imi.global.security.jwt.service.JwtService;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResDto login(String email, String password) {
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(SignInFailedException::new);
        if (passwordEncoder.matches(password, user.getPassword())) {
            TokenDto accessToken = jwtService.issueAccessToken(user.getEmail());
            String refreshToken = jwtService.issueRefreshToken(user.getEmail());
            return new LoginResDto(accessToken.token(), refreshToken, accessToken.expiresIn(), new Date().getTime());
        } else {
            throw new SignInFailedException();
        }
    }

    public LoginResDto refresh(String refreshToken) {
        if (jwtService.validateRefreshToken(refreshToken)) {
            String userId = jwtService.extractUserId(refreshToken);
            TokenDto accessToken = jwtService.issueAccessToken(userId);
            String newRefreshToken = jwtService.issueRefreshToken(userId);
            jwtService.deleteRefreshToken(refreshToken);
            return new LoginResDto(accessToken.token(), newRefreshToken, accessToken.expiresIn(), System.currentTimeMillis());
        } else {
            throw new InvalidRefreshTokenException();
        }
    }
}