package br.com.tiagoschermack.family_tree.dto.response;

import br.com.tiagoschermack.family_tree.domain.enums.ParticipationRole;

public record EventParticipationResponse(
        String personId,
        String firstName,
        String lastName,
        ParticipationRole role
) {}
