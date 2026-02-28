package br.com.tiagoschermack.family_tree.infrastructure.persistence.impl;

import br.com.tiagoschermack.family_tree.domain.entity.LifeEvent;
import br.com.tiagoschermack.family_tree.domain.repository.LifeEventRepository;
import br.com.tiagoschermack.family_tree.infrastructure.persistence.neo4j.LifeEventNeo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LifeEventRepositoryImpl implements LifeEventRepository {

    private final LifeEventNeo4jRepository neo4jRepository;

    public LifeEventRepositoryImpl(LifeEventNeo4jRepository neo4jRepository) {
        this.neo4jRepository = neo4jRepository;
    }

    @Override
    public LifeEvent save(LifeEvent lifeEvent) {
        return neo4jRepository.save(lifeEvent);
    }

    @Override
    public Optional<LifeEvent> findById(String id) {
        return neo4jRepository.findByIdAndDeletedFalse(id);
    }

    @Override
    public List<LifeEvent> findAllByPersonId(String personId) {
        return neo4jRepository.findAllByPersonId(personId);
    }

    @Override
    public void deleteById(String id) {
        neo4jRepository.deleteById(id);
    }
}
