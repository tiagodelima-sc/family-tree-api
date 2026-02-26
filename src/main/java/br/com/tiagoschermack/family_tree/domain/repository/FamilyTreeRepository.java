package br.com.tiagoschermack.family_tree.domain.repository;

import br.com.tiagoschermack.family_tree.domain.entity.FamilyTree;

import java.util.Optional;

public interface FamilyTreeRepository {

    FamilyTree save(FamilyTree familyTree);
    Optional<FamilyTree> findById(String id);
}
