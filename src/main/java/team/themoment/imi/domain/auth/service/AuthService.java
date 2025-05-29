package team.themoment.imi.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.themoment.imi.domain.auth.data.response.LoginResDto;
import team.themoment.imi.domain.auth.exception.AuthCodeExpiredException;
import team.themoment.imi.domain.auth.exception.InvalidAuthCodeException;
import team.themoment.imi.domain.auth.exception.InvalidRefreshTokenException;
import team.themoment.imi.domain.auth.exception.SignInFailedException;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.domain.user.repository.UserJpaRepository;
import team.themoment.imi.global.email.entity.AuthCode;
import team.themoment.imi.global.email.entity.Authentication;
import team.themoment.imi.global.email.exception.ExcessiveAuthAttemptsException;
import team.themoment.imi.global.email.repository.AuthCodeRedisRepository;
import team.themoment.imi.global.email.repository.AuthenticationRedisRepository;
import team.themoment.imi.global.email.service.EmailService;
import team.themoment.imi.global.security.jwt.data.TokenDto;
import team.themoment.imi.global.security.jwt.service.JwtService;

import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserJpaRepository userJpaRepository;
    private final AuthCodeRedisRepository authCodeRedisRepository;
    private final AuthenticationRedisRepository authenticationRedisRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private static final int MAX_AUTH_ATTEMPTS = 5;
    private static final long AUTH_CODE_EXPIRATION_TIME = 5 * 60 * 1000; // 5분

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

    public void sendEmail(String email) {
        Authentication authentication = authenticationRedisRepository.findById(email)
                .orElse(Authentication.builder()
                        .email(email)
                        .attempts(0)
                        .verified(false)
                        .expiration(AUTH_CODE_EXPIRATION_TIME)
                        .build());
        if (authentication.getAttempts() >= MAX_AUTH_ATTEMPTS) {
            throw new ExcessiveAuthAttemptsException();
        }
        String authCode = generateRandomCode();
        AuthCode authCodeEntity = AuthCode.builder()
                .email(email)
                .authCode(authCode)
                .expiration(AUTH_CODE_EXPIRATION_TIME)
                .build();
        authCodeRedisRepository.save(authCodeEntity);
        authentication = Authentication.builder()
                .email(authentication.getEmail())
                .attempts(authentication.getAttempts() + 1)
                .verified(false)
                .expiration(AUTH_CODE_EXPIRATION_TIME)
                .build();
        authenticationRedisRepository.save(authentication);
        emailService.sendAuthenticationEmail(email, authCode);
    }

    public void verifyAuthCode(int authCode) {
        AuthCode savedAuthCode = authCodeRedisRepository.findByAuthCode(String.valueOf(authCode))
                .orElseThrow(InvalidAuthCodeException::new);
        String email = savedAuthCode.getEmail();
        Authentication authentication = authenticationRedisRepository.findById(email)
                .orElseThrow(AuthCodeExpiredException::new);
        Authentication updatedAuthentication = Authentication.builder()
                .email(email)
                .attempts(authentication.getAttempts())
                .verified(true)
                .expiration(authentication.getExpiration())
                .build();
        authenticationRedisRepository.save(updatedAuthentication);
        authCodeRedisRepository.deleteById(email);
    }

    private String generateRandomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}