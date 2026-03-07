package br.com.tiagoschermack.family_tree.domain.repository;

import br.com.tiagoschermack.family_tree.domain.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    Person save(Person person);

    Optional<Person> findById(String id);

    Optional<Person> findByIdAndTreeId(String personId, String treeId);

    List<Person> findAllByTreeIdAndUserId(String treeId, String userId);

    List<Person> findAllByEventIdAndTreeId(String eventId, String treeId);
}
