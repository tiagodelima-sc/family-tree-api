package br.com.tiagoschermack.family_tree.infrastructure.web.controller;

import br.com.tiagoschermack.family_tree.applicationservice.UserService;
import br.com.tiagoschermack.family_tree.dto.request.UpdateUserRequest;
import br.com.tiagoschermack.family_tree.dto.response.UserResponse;
import br.com.tiagoschermack.family_tree.shared.security.UserAuthenticated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static br.com.tiagoschermack.family_tree.shared.constants.RestConstants.PATH_USERS;

@Tag(name = "Usuários", description = "Gerenciamento do usuário registrado")
@RestController
@RequestMapping(PATH_USERS)
@SecurityRequirement(name = "Bearer Authentication")
@SuppressWarnings("unused")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary     = "Buscar perfil",
            description = "Retorna os dados do usuário registrado"
    )
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal UserAuthenticated userAuthenticated) {
        return ResponseEntity.ok(userService.getUser(userAuthenticated.getUser().getId()));
    }

    @Operation(
            summary     = "Atualizar perfil",
            description = "Atualiza nome, e-mail ou senha do usuário registrado"
    )
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @RequestBody @Valid UpdateUserRequest userRequest) {
        return ResponseEntity.ok(userService.updateUser(userAuthenticated.getUser().getId(), userRequest));
    }

    @Operation(
            summary     = "Deletar conta",
            description = "Remove a conta do usuário registrado"
    )
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal UserAuthenticated userAuthenticated) {
        userService.deleteUser(userAuthenticated.getUser().getId());

        return ResponseEntity.noContent().build();
    }
}
