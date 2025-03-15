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
import team.themoment.imi.global.security.auth.CustomUserDetails;
import team.themoment.imi.global.security.exception.ExpiredJwtTokenException;
import team.themoment.imi.global.security.exception.InvalidJwtTokenException;
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
        if (request.getRequestURI().startsWith("/auth")) {
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
                        UserDetails userDetails = new CustomUserDetails(username);
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (ExpiredJwtTokenException e) {
                setErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT token has expired");
                return;
            } catch (InvalidJwtTokenException e) {
                setErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid JWT token");
                return;
            }
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