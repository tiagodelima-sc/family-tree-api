package br.com.tiagoschermack.family_tree.domain.entity;

import br.com.tiagoschermack.family_tree.domain.relationship.TreeAccess;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node("User")
public class User {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    private String name;
    private String email;
    private String password;

    private boolean deleted;

    @Relationship(type = "HAS_ACCESS", direction = OUTGOING)
    @SuppressWarnings("FieldMayBeFinal")
    private List<TreeAccess> trees = new ArrayList<>();

    public User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public List<TreeAccess> getTrees() {
        return trees;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void addTree(TreeAccess treeAccess) {
        this.trees.add(treeAccess);
    }
}
