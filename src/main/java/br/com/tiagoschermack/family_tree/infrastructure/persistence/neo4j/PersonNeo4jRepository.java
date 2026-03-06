package br.com.tiagoschermack.family_tree.infrastructure.persistence.neo4j;

import br.com.tiagoschermack.family_tree.domain.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface PersonNeo4jRepository extends Neo4jRepository<Person, String> {

    Optional<Person> findByIdAndDeletedFalse(String id);

    @Query("""
        MATCH (p:Person {id: $personId})-[:BELONGS_TO]->(f:FamilyTree {id: $treeId})
        WHERE p.deleted = false
        RETURN p
        """)
    Optional<Person> findByIdAndTreeId(String personId, String treeId);

    @Query("""
        MATCH (u:User {id: $userId})-[:HAS_ACCESS]->(f:FamilyTree {id: $treeId})
        MATCH (p:Person)-[:BELONGS_TO]->(f)
        WHERE p.deleted = false
        RETURN p
        """)
    List<Person> findAllByTreeIdAndUserId(String treeId, String userId);

    @Query("MATCH (p:Person {id: $id}) SET p.deleted = true")
    void deleteById(String id);
}
