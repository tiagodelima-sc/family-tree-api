package br.com.tiagoschermack.family_tree.infrastructure.persistence.impl;

import br.com.tiagoschermack.family_tree.domain.entity.Person;
import br.com.tiagoschermack.family_tree.domain.repository.PersonRepository;
import br.com.tiagoschermack.family_tree.infrastructure.persistence.neo4j.PersonNeo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final PersonNeo4jRepository neo4jRepository;

    public PersonRepositoryImpl(PersonNeo4jRepository neo4jRepository) {
        this.neo4jRepository = neo4jRepository;
    }

    @Override
    public Person save(Person person) {
        return neo4jRepository.save(person);
    }

    @Override
    public Optional<Person> findById(String id) {
        return neo4jRepository.findByIdAndDeletedFalse(id);
    }

    @Override
    public List<Person> findAllByTreeId(String treeId) {
        return neo4jRepository.findAllByTreeId(treeId);
    }

    @Override
    public void deleteById(String id) {
        neo4jRepository.deleteById(id);
    }
}
