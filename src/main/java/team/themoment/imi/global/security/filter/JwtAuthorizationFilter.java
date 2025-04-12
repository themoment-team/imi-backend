package team.themoment.imi.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.global.security.auth.CustomUserDetails;
import team.themoment.imi.global.security.exception.ExpiredAccessTokenException;
import team.themoment.imi.global.security.exception.InvalidAccessTokenException;
import team.themoment.imi.global.security.jwt.service.JwtParserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtParserService jwtParserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/auth")
                || (request.getRequestURI().startsWith("/profile/") && !request.getRequestURI().equals("/profile/my"))
                || request.getRequestURI().equals("/profile/list")
                || request.getRequestURI().equals("/user/join")
                || request.getRequestURI().equals("/user/checkEmail")
                || request.getRequestURI().startsWith("/club")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                if (jwtParserService.validateToken(token)) {
                    String username = jwtParserService.extractUserId(token);
                    if (username != null) {
                        UserDetails userDetails = new CustomUserDetails(User.builder().email(username).build());
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (ExpiredAccessTokenException e) {
                setErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT token has expired");
                return;
            } catch (InvalidAccessTokenException e) {
                setErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid JWT token");
                return;
            }
        } else {
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT token is missing");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        PrintWriter writer = response.getWriter();
        writer.write(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(errorResponse));
        writer.flush();
    }
}