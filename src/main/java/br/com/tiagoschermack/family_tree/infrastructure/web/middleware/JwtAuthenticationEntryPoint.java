package br.com.tiagoschermack.family_tree.infrastructure.web.middleware;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus        (SC_UNAUTHORIZED);
        response.setContentType   ("application/json;charset=UTF-8");
        response.getWriter().write("""
                {"message": "Autenticação necessária para acessar este recurso", "path": "%s", "fieldErrors": null}
                """.formatted(request.getRequestURI()));
    }
}