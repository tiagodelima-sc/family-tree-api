package br.com.tiagoschermack.family_tree.applicationservice;

import br.com.tiagoschermack.family_tree.domain.entity.FamilyTree;
import br.com.tiagoschermack.family_tree.domain.entity.User;
import br.com.tiagoschermack.family_tree.domain.relationship.TreeAccess;
import br.com.tiagoschermack.family_tree.domain.repository.FamilyTreeRepository;
import br.com.tiagoschermack.family_tree.domain.repository.UserRepository;
import br.com.tiagoschermack.family_tree.dto.request.CreateFamilyTreeRequest;
import br.com.tiagoschermack.family_tree.dto.request.UpdateFamilyTreeRequest;
import br.com.tiagoschermack.family_tree.dto.response.FamilyTreeResponse;
import br.com.tiagoschermack.family_tree.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.tiagoschermack.family_tree.domain.enums.AccessRole.OWNER;

@Service
public class FamilyTreeService {

    private final FamilyTreeRepository familyTreeRepository;
    private final UserRepository userRepository;

    public FamilyTreeService(FamilyTreeRepository familyTreeRepository, UserRepository userRepository) {
        this.familyTreeRepository = familyTreeRepository;
        this.userRepository       = userRepository;
    }

    public FamilyTreeResponse createTree(String userId, CreateFamilyTreeRequest treeRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("O usuário não foi encontrado"));

        FamilyTree treeSaved = familyTreeRepository.save(new FamilyTree(treeRequest.name(), treeRequest.description(), treeRequest.isPublic()));

        user.addTree(new TreeAccess(OWNER, treeSaved));
        userRepository.save(user);

        return FamilyTreeResponse.of(treeSaved);
    }

    public FamilyTreeResponse getTree(String treeId, String userId) {
        return FamilyTreeResponse.of(findTreeByIdAndUserId(treeId, userId));
    }

    public List<FamilyTreeResponse> getTrees(String userId) {
        return familyTreeRepository.findAllByUserId(userId)
                .stream()
                .map   (FamilyTreeResponse::of)
                .toList();
    }

    public FamilyTreeResponse updateTree(String treeId, String userId, UpdateFamilyTreeRequest treeRequest) {
        FamilyTree existingTree = findTreeByIdAndUserId(treeId, userId);

        if (treeRequest.name() != null)
            existingTree.setName(treeRequest.name());

        if (treeRequest.description() != null)
            existingTree.setDescription(treeRequest.description());

        if (treeRequest.isPublic() != null)
            existingTree.setPublic(treeRequest.isPublic());

        return FamilyTreeResponse.of(familyTreeRepository.save(existingTree));

    }

    public void deleteTree(String treeId, String userId) {
        FamilyTree existingTree = findTreeByIdAndUserId(treeId, userId);
        existingTree.setDeleted(true);

        familyTreeRepository.save(existingTree);
    }

    private FamilyTree findTreeByIdAndUserId(String treeId, String userId) {
        return familyTreeRepository.findByIdAndUserId(treeId, userId)
                .orElseThrow(() -> new NotFoundException("Árvore genealógica não encontrada"));
    }
}
