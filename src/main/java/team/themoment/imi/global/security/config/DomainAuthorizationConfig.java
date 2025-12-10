package team.themoment.imi.global.security.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

@Component
public class DomainAuthorizationConfig {

    public void configureAuthorization(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // Auth
                .requestMatchers("/auth/**").permitAll()

                // Profile
                .requestMatchers(HttpMethod.GET, "/profile/{studentId}").permitAll()
                .requestMatchers(HttpMethod.GET, "/profile/list").permitAll()
                .requestMatchers(HttpMethod.GET, "/profile/my").authenticated()
                .requestMatchers(HttpMethod.PUT, "/profile").authenticated()

                // User
                .requestMatchers(HttpMethod.POST, "/user/join").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/check-email").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/user/password").permitAll()
                .requestMatchers(HttpMethod.PUT, "/user").authenticated()

                // Club
                .requestMatchers(HttpMethod.GET, "/club").permitAll()

                .anyRequest().authenticated()
        );
    }
}