package team.themoment.imi.domain.auth.service;

import team.themoment.imi.domain.auth.data.response.LoginResDto;

public interface LogInService {
    LoginResDto execute(String email, String password);
}