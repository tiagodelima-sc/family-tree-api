package br.com.tiagoschermack.family_tree.infrastructure.persistence.neo4j;

import br.com.tiagoschermack.family_tree.domain.entity.FamilyTree;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface FamilyTreeNeo4jRepository extends Neo4jRepository<FamilyTree, String> {

    Optional<FamilyTree> findByIdAndDeletedFalse(String id);

    @Query("MATCH (u:User {id: $userId})-[:HAS_ACCESS]->(f:FamilyTree) WHERE f.deleted = false RETURN f")
    List<FamilyTree> findAllByUserId(String userId);

    @Query("MATCH (f:FamilyTree {id: $id}) SET f.deleted = true")
    void deleteById(String id);
}
