package br.com.tiagoschermack.family_tree.dto.request;

import br.com.tiagoschermack.family_tree.domain.enums.LifeEventType;

import java.time.LocalDate;

public record UpdateLifeEventRequest(
        LifeEventType type,
        LocalDate date,
        String place,
        String description
) {}
