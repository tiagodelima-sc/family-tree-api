package br.com.tiagoschermack.family_tree.infrastructure.web.middleware;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus        (SC_FORBIDDEN);
        response.setContentType   ("application/json;charset=UTF-8");
        response.getWriter().write("""
                {"message": "Você não tem permissão para acessar este recurso", "path": "%s", "fieldErrors": null}
                """.formatted(request.getRequestURI()));
    }
}