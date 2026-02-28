package br.com.tiagoschermack.family_tree.infrastructure.persistence.impl;

import br.com.tiagoschermack.family_tree.domain.entity.User;
import br.com.tiagoschermack.family_tree.domain.repository.UserRepository;
import br.com.tiagoschermack.family_tree.infrastructure.persistence.neo4j.UserNeo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserNeo4jRepository neo4jRepository;

    public UserRepositoryImpl(UserNeo4jRepository neo4jRepository) {
        this.neo4jRepository = neo4jRepository;
    }

    @Override
    public User save(User user) {
        return neo4jRepository.save(user);
    }

    @Override
    public Optional<User> findById(String id) {
        return neo4jRepository.findByIdAndDeletedFalse(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return neo4jRepository.findByEmailAndDeletedFalse(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return neo4jRepository.existsByEmailAndDeletedFalse(email);
    }

    @Override
    public void deleteById(String id) {
        neo4jRepository.deleteById(id);
    }
}
