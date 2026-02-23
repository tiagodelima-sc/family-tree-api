package br.com.tiagoschermack.family_tree.domain.relationship;

import br.com.tiagoschermack.family_tree.domain.entity.FamilyTree;
import br.com.tiagoschermack.family_tree.domain.enums.AccessRole;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class TreeAccess {

    @RelationshipId
    private Long id;

    private AccessRole role;

    @TargetNode
    private FamilyTree tree;

    protected TreeAccess() {}

    public TreeAccess(AccessRole role, FamilyTree tree) {
        this.role = role;
        this.tree = tree;
    }

    public Long getId() {
        return id;
    }

    public AccessRole getRole() {
        return role;
    }

    public FamilyTree getTree() {
        return tree;
    }

    public void setRole(AccessRole role) {
        this.role = role;
    }
}
