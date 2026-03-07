package br.com.tiagoschermack.family_tree.infrastructure.web.controller;

import br.com.tiagoschermack.family_tree.applicationservice.UserService;
import br.com.tiagoschermack.family_tree.dto.request.UpdateUserRequest;
import br.com.tiagoschermack.family_tree.dto.response.UserResponse;
import br.com.tiagoschermack.family_tree.shared.security.UserAuthenticated;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static br.com.tiagoschermack.family_tree.shared.constants.RestConstants.PATH_USERS;

@RestController
@RequestMapping(PATH_USERS)
@SuppressWarnings("unused")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal UserAuthenticated userAuthenticated) {
        return ResponseEntity.ok(userService.getUser(userAuthenticated.getUser().getId()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal UserAuthenticated userAuthenticated, @RequestBody @Valid UpdateUserRequest userRequest) {
        return ResponseEntity.ok(userService.updateUser(userAuthenticated.getUser().getId(), userRequest));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal UserAuthenticated userAuthenticated) {
        userService.deleteUser(userAuthenticated.getUser().getId());

        return ResponseEntity.noContent().build();
    }
}
