package br.com.tiagoschermack.family_tree.infrastructure.persistence.neo4j;

import br.com.tiagoschermack.family_tree.domain.entity.LifeEvent;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface LifeEventNeo4jRepository extends Neo4jRepository<LifeEvent, String> {

    Optional<LifeEvent> findByIdAndDeletedFalse(String id);

    @Query("MATCH (p:Person {id: $personId})-[:PARTICIPATED_IN]->(l:LifeEvent) WHERE l.deleted = false RETURN l")
    List<LifeEvent> findAllByPersonId(String personId);

    @Query("MATCH (l:LifeEvent {id: $id}) SET l.deleted = true")
    void deleteById(String id);
}
