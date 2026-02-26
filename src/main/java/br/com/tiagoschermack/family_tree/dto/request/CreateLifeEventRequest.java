package br.com.tiagoschermack.family_tree.dto.request;

import br.com.tiagoschermack.family_tree.domain.enums.LifeEventType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record CreateLifeEventRequest(
        @NotNull(message = "O tipo do evento é obrigatório")
        LifeEventType type,

        @NotNull(message = "A data do evento é obrigatória")
        LocalDate date,

        String place,

        @Size(max = 600, message = "O tamanho máximo da descrição é 600 caracteres")
        String description,

        @NotEmpty(message = "Um evento deve ter pelo menos um participante")
        @Valid
        List<EventParticipantRequest> participants
) {}
