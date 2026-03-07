package br.com.tiagoschermack.family_tree.infrastructure.web.controller;

import br.com.tiagoschermack.family_tree.applicationservice.AuthService;
import br.com.tiagoschermack.family_tree.dto.request.CreateUserRequest;
import br.com.tiagoschermack.family_tree.dto.request.LoginUserRequest;
import br.com.tiagoschermack.family_tree.dto.response.TokenResponse;
import br.com.tiagoschermack.family_tree.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.tiagoschermack.family_tree.shared.constants.RestConstants.PATH_AUTH;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(PATH_AUTH)
@SuppressWarnings("unused")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid CreateUserRequest userRequest) {
        return ResponseEntity.status(CREATED).body(authService.register(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginUserRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
