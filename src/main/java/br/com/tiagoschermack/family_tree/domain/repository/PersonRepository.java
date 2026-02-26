package br.com.tiagoschermack.family_tree.domain.repository;

import br.com.tiagoschermack.family_tree.domain.entity.Person;

import java.util.Optional;

public interface PersonRepository {

    Person save(Person person);
    Optional<Person> findById(String id);
}
