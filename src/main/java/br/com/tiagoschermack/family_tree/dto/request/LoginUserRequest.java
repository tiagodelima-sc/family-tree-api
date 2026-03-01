package br.com.tiagoschermack.family_tree.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserRequest(
        @NotBlank(message = "O e-mail do usuário é obrigatório")
        @Email(message = "Esse formato de e-mail não é válido")
        String email,

        @NotBlank(message = "A senha do usuário é obrigatório")
        String password
) {}
