package br.com.tiagoschermack.family_tree.infrastructure.web.controller;

import br.com.tiagoschermack.family_tree.applicationservice.AuthService;
import br.com.tiagoschermack.family_tree.dto.request.CreateUserRequest;
import br.com.tiagoschermack.family_tree.dto.request.LoginUserRequest;
import br.com.tiagoschermack.family_tree.dto.response.TokenResponse;
import br.com.tiagoschermack.family_tree.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.tiagoschermack.family_tree.shared.constants.RestConstants.PATH_AUTH;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(name = "Autenticação", description = "Registro e login de usuários")
@RestController
@RequestMapping(PATH_AUTH)
@SecurityRequirements
@SuppressWarnings("unused")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary     = "Registrar usuário",
            description = "Registrar uma nova conta com nome, e-mail e senha"
    )
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid CreateUserRequest userRequest) {
        return ResponseEntity.status(CREATED).body(authService.register(userRequest));
    }

    @Operation(
            summary     = "Login",
            description = "Autenticar o usuário e retorna um token"
    )
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginUserRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
