package br.com.tiagoschermack.family_tree.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank(message = "O nome do usuário é obrigatório")
        String name,

        @NotBlank(message = "O e-mail do usuário é obrigatório")
        String email,

        @NotBlank(message = "A senha do usuário é obrigatório")
        String password
) {}
