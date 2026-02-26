package br.com.tiagoschermack.family_tree.dto.request;

import br.com.tiagoschermack.family_tree.domain.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePersonRequest(
        @NotBlank(message = "O nome da pessoa é obrigatório")
        @Size(max = 30, message = "O tamanho máximo do nome é 30 caracteres")
        String firstName,

        @NotBlank(message = "O sobrenome da pessoa é obrigatório")
        @Size(max = 50, message = "O tamanho máximo do nome é 50 caracteres")
        String lastName,

        @NotNull(message = "O gênero da pessoa é obrigatório")
        Gender gender
) {}

