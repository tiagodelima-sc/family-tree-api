package br.com.tiagoschermack.family_tree.domain.repository;

import br.com.tiagoschermack.family_tree.domain.entity.FamilyTree;

import java.util.List;
import java.util.Optional;

public interface FamilyTreeRepository {

    FamilyTree save(FamilyTree familyTree);

    Optional<FamilyTree> findById(String id);

    List<FamilyTree> findAllByUserId(String userId);

    void deleteById(String id);
}
