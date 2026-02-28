package br.com.tiagoschermack.family_tree.infrastructure.persistence.neo4j;

import br.com.tiagoschermack.family_tree.domain.entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;

public interface UserNeo4jRepository extends Neo4jRepository<User, String> {

    Optional<User> findByIdAndDeletedFalse   (String id);
    Optional<User> findByEmailAndDeletedFalse(String email);

    boolean existsByEmailAndDeletedFalse(String email);

    @Query("MATCH (u:User {id: $id}) SET u.deleted = true")
    void deleteById(String id);
}
