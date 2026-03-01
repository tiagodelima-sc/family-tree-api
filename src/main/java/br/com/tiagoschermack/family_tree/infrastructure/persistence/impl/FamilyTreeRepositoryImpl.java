package br.com.tiagoschermack.family_tree.infrastructure.persistence.impl;

import br.com.tiagoschermack.family_tree.domain.entity.FamilyTree;
import br.com.tiagoschermack.family_tree.domain.repository.FamilyTreeRepository;
import br.com.tiagoschermack.family_tree.infrastructure.persistence.neo4j.FamilyTreeNeo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FamilyTreeRepositoryImpl implements FamilyTreeRepository {

    private final FamilyTreeNeo4jRepository neo4jRepository;

    public FamilyTreeRepositoryImpl(FamilyTreeNeo4jRepository neo4jRepository) {
        this.neo4jRepository = neo4jRepository;
    }

    @Override
    public FamilyTree save(FamilyTree familyTree) {
        return neo4jRepository.save(familyTree);
    }

    @Override
    public Optional<FamilyTree> findByIdAndUserId(String id, String userId) {
        return neo4jRepository.findByIdAndUserId(id, userId);
    }

    @Override
    public List<FamilyTree> findAllByUserId(String userId) {
        return neo4jRepository.findAllByUserId(userId);
    }
}
