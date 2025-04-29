package team.themoment.imi.global.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

@Component
public class DomainAuthorizationConfig {

    public void configureAuthorization(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()

                .requestMatchers("/profile/{studentId}").permitAll()
                .requestMatchers("/profile/list").permitAll()
                .requestMatchers("/profile/my").authenticated()
                .requestMatchers("/profile").permitAll()

                .requestMatchers("/user/join").permitAll()
                .requestMatchers("/user/check-email").permitAll()
                .requestMatchers("/user/password").authenticated()
                .requestMatchers("/user").authenticated()

                .requestMatchers("/club").permitAll()
                .anyRequest().authenticated()
        );
    }
}