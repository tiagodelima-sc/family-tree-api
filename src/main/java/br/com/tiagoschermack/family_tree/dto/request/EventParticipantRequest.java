package br.com.tiagoschermack.family_tree.dto.request;

import br.com.tiagoschermack.family_tree.domain.enums.ParticipationRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventParticipantRequest(
        @NotBlank(message = "O ID da pessoa é obrigatório")
        String personId,

        @NotNull(message = "Defina o papel do participante no evento")
        ParticipationRole role
) {}
