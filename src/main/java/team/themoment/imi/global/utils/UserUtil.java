package team.themoment.imi.global.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.domain.user.exception.MemberNotFoundException;
import team.themoment.imi.domain.user.repository.UserJpaRepository;
import team.themoment.imi.global.utils.exception.UnauthenticatedException;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserJpaRepository userJpaRepository;

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userJpaRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(MemberNotFoundException::new);
        } else {
            throw new UnauthenticatedException();
        }
    }
}