package br.com.tiagoschermack.family_tree.domain.relationship;

import br.com.tiagoschermack.family_tree.domain.entity.LifeEvent;
import br.com.tiagoschermack.family_tree.domain.enums.ParticipationRole;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class EventParticipation {

    @RelationshipId
    private Long id;

    private ParticipationRole role;

    @TargetNode
    private LifeEvent event;

    protected EventParticipation() {}

    public EventParticipation(ParticipationRole role, LifeEvent event) {
        this.role = role;
        this.event = event;
    }

    public Long getId() {
        return id;
    }

    public ParticipationRole getRole() {
        return role;
    }

    public LifeEvent getEvent() {
        return event;
    }

    public void setRole(ParticipationRole role) {
        this.role = role;
    }
}
