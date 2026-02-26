package br.com.tiagoschermack.family_tree.domain.entity;

import br.com.tiagoschermack.family_tree.domain.enums.LifeEventType;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDate;

@Node("LifeEvent")
public class LifeEvent {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    private LifeEventType type;

    private LocalDate date;

    private String place;
    private String description;

    private boolean deleted;

    protected LifeEvent() {}

    public LifeEvent(LifeEventType type, LocalDate date, String place, String description) {
        this.type = type;
        this.date = date;
        this.place = place;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public LifeEventType getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setType(LifeEventType type) {
        this.type = type;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
