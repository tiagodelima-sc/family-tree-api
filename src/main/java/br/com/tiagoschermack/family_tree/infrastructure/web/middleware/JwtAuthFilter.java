package br.com.tiagoschermack.family_tree.infrastructure.web.middleware;

import br.com.tiagoschermack.family_tree.domain.repository.UserRepository;
import br.com.tiagoschermack.family_tree.shared.security.JwtService;
import br.com.tiagoschermack.family_tree.shared.security.UserAuthenticated;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService     = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtService.isTokenValid(token)) {
            response.setStatus(SC_UNAUTHORIZED);
            return;
        }

        authenticateUser(token, request);
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return null;

        String token = authHeader.substring(7).trim();
        if (token.isEmpty())
            return null;

        return token;
    }

    private void authenticateUser(String token, HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication() != null)
            return;

        String userId = jwtService.extractUserId(token);

        userRepository.findById(userId).ifPresent(user -> {
            UserAuthenticated userAuthenticated = new UserAuthenticated(user);

            if (userAuthenticated.isEnabled()) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userAuthenticated,
                                null,
                                userAuthenticated.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        });
    }
}
