package team.themoment.imi.domain.auth.service;

import team.themoment.imi.domain.auth.data.response.LoginResDto;

public interface RefreshService {
    LoginResDto execute(String refreshToken);
}