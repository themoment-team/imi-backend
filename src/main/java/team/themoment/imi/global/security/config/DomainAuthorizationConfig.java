package team.themoment.imi.global.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

@Component
public class DomainAuthorizationConfig {

    public void configureAuthorization(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/user/join").permitAll()
                .requestMatchers("/user/checkEmail").permitAll()
                .requestMatchers("/user/**").authenticated()
                .requestMatchers("/club/**").authenticated()
                .anyRequest().authenticated()
        );
    }
}