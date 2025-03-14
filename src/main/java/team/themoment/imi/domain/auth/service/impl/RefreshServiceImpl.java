package team.themoment.imi.domain.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.imi.domain.auth.data.response.LoginResDto;
import team.themoment.imi.domain.auth.exception.InvalidRefreshTokenException;
import team.themoment.imi.domain.auth.service.RefreshService;
import team.themoment.imi.global.security.jwt.service.JwtIssueService;
import team.themoment.imi.global.security.jwt.service.JwtParserService;

@Service
@RequiredArgsConstructor
public class RefreshServiceImpl implements RefreshService {

    private final JwtIssueService jwtIssueService;
    private final JwtParserService jwtParserService;

    @Override
    public LoginResDto execute(String refreshToken) {
        if (jwtParserService.validateRefreshToken(refreshToken)) {
            String userId = jwtParserService.extractUserId(refreshToken);
            return new LoginResDto(jwtIssueService.issueAccessToken(userId).token(), refreshToken, jwtIssueService.issueAccessToken(userId).expiresIn(), System.currentTimeMillis());
        } else {
            throw new InvalidRefreshTokenException();
        }
    }
}