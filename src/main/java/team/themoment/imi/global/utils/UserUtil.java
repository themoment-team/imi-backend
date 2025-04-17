package team.themoment.imi.global.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.domain.user.repository.UserJpaRepository;
import team.themoment.imi.global.exception.GlobalException;

@Component
@RequiredArgsConstructor
public class UserUtil {
    private final UserJpaRepository userJpaRepository;
    public User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails userDetails){
            return userJpaRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new GlobalException("Login User not found", HttpStatus.NOT_FOUND));
        }else {
            throw new GlobalException("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }
}