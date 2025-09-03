package team.themoment.imi.global.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import team.themoment.imi.domain.user.entity.User;
import team.themoment.imi.global.security.auth.CustomUserDetails;
import team.themoment.imi.global.security.exception.ExpiredAccessTokenException;
import team.themoment.imi.global.security.exception.InvalidAccessTokenException;
import team.themoment.imi.global.security.jwt.service.JwtService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final String[] excludedPaths = {
            "/auth",
            "/profile/",
            "/profile/list",
            "/user/join",
            "/user/check-email",
            "/user/password",
            "/club"
    };
    private final String[] excludedExactPaths = {
            "/profile/my"
    };

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (shouldSkipFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = extractTokenFromHeader(request);
        if (token == null) {
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT 토큰이 없습니다.");
            return;
        }
        try {
            if (jwtService.validateToken(token)) {
                String username = jwtService.extractUserId(token);
                if (username != null) {
                    UserDetails userDetails = new CustomUserDetails(User.builder().email(username).build());
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (ExpiredAccessTokenException | ExpiredJwtException e) {
            log.debug("Access token expired for request: {}", request.getRequestURI());
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT 토큰이 만료되었습니다.");
            return;
        } catch (Exception e) {
            log.debug("Invalid access token for request: {}", request.getRequestURI());
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다.");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean shouldSkipFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        for (String path : excludedPaths) {
            if (pathMatcher.matchStart(path, uri)) {
                for (String exactPath : excludedExactPaths) {
                    if (uri.equals(exactPath)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(7);
        }
        return null;
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