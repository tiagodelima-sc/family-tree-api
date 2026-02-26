package br.com.tiagoschermack.family_tree.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateFamilyTreeRequest(

        @NotBlank(message = "O nome da árvore genealógica é obrigatória.")
        @Size(max = 30, message = "O tamanho máximo do nome é 30 caracteres")
        String name,

        @Size(max = 600, message = "O tamanho máximo da descrição é 600 caracteres")
        String description,

        @NotNull(message = "A da visibilidade da árvore é obrigatória")
        Boolean isPublic
) {}
