package br.com.tiagoschermack.family_tree.infrastructure.persistence.neo4j;

import br.com.tiagoschermack.family_tree.domain.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface PersonNeo4jRepository extends Neo4jRepository<Person, String> {

    Optional<Person> findByIdAndDeletedFalse(String id);

    @Query("MATCH (p:Person)-[:BELONGS_TO]->(f:FamilyTree {id: $treeId}) WHERE p.deleted = false RETURN p")
    List<Person> findAllByTreeId(String treeId);

    @Query("MATCH (p:Person {id: $id}) SET p.deleted = true")
    void deleteById(String id);
}
