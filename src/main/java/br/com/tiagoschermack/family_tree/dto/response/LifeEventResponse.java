package br.com.tiagoschermack.family_tree.dto.response;

import br.com.tiagoschermack.family_tree.domain.entity.LifeEvent;
import br.com.tiagoschermack.family_tree.domain.enums.LifeEventType;

import java.time.LocalDate;
import java.util.List;

public record LifeEventResponse(
        String id,
        LifeEventType type,
        LocalDate date,
        String place,
        String description,
        List<EventParticipationResponse> participants
) {
    public static LifeEventResponse of(LifeEvent event, List<EventParticipationResponse> participants) {
        return new LifeEventResponse(
                event.getId(),
                event.getType(),
                event.getDate(),
                event.getPlace(),
                event.getDescription(),
                participants
        );
    }
}
