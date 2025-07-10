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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (shouldSkipFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = extractTokenFromHeader(request);
        if (token == null) {
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT token is missing");
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
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT token has expired");
            return;
        } catch (Exception e) {
            log.debug("Invalid access token for request: {}", request.getRequestURI());
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid JWT token");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean shouldSkipFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.startsWith("/auth")
                || (uri.startsWith("/profile/") && !uri.equals("/profile/my"))
                || uri.equals("/profile/list")
                || uri.equals("/user/join")
                || uri.equals("/user/check-email")
                || uri.equals("/user/password")
                || uri.startsWith("/club");
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
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