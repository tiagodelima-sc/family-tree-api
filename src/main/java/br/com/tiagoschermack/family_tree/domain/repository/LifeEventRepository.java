package br.com.tiagoschermack.family_tree.domain.repository;

import br.com.tiagoschermack.family_tree.domain.entity.LifeEvent;

import java.util.List;
import java.util.Optional;

public interface LifeEventRepository {

    LifeEvent save(LifeEvent lifeEvent);

    Optional<LifeEvent> findById(String id);

    List<LifeEvent> findAllByPersonId(String personId);

    void deleteById(String id);
}
