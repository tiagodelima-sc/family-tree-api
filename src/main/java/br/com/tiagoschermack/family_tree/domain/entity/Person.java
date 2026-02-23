package br.com.tiagoschermack.family_tree.domain.entity;

import br.com.tiagoschermack.family_tree.domain.enums.Gender;
import br.com.tiagoschermack.family_tree.domain.relationship.EventParticipation;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node("Person")
public class Person {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    @Property("first_name")
    private String firstName;

    @Property("last_name")
    private String lastName;

    private Gender gender;

    @Relationship(type = "BELONGS_TO", direction = OUTGOING)
    private FamilyTree familyTree;

    @Relationship(type = "PARENT_OF", direction = OUTGOING)
    private List<Person> children = new ArrayList<>();

    @Relationship(type = "PARENT_OF", direction = INCOMING)
    private List<Person> parents = new ArrayList<>();

    @Relationship(type = "PARTICIPATED_IN", direction = OUTGOING)
    @SuppressWarnings("FieldMayBeFinal")
    private List<EventParticipation> lifeEvents = new ArrayList<>();

    protected Person() {}

    public Person(String firstName, String lastName, Gender gender, FamilyTree familyTree) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.familyTree = familyTree;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public FamilyTree getFamilyTree() {
        return familyTree;
    }

    public List<Person> getChildren() {
        return children;
    }

    public List<Person> getParents() {
        return parents;
    }

    public List<EventParticipation> getLifeEvents() {
        return lifeEvents;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setFamilyTree(FamilyTree familyTree) {
        this.familyTree = familyTree;
    }

    public void setChildren(List<Person> children) {
        this.children = children;
    }

    public void setParents(List<Person> parents) {
        this.parents = parents;
    }

    public void addLifeEvent(EventParticipation participation) {
        this.lifeEvents.add(participation);
    }
}
