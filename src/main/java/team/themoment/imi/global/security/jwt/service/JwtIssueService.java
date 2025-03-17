package team.themoment.imi.global.security.jwt.service;

import team.themoment.imi.global.security.jwt.data.TokenDto;

public interface JwtIssueService {
    TokenDto issueAccessToken(String userId);

    String issueRefreshToken(String userId);
}