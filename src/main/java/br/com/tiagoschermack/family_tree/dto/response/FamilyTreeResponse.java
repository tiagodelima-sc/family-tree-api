package br.com.tiagoschermack.family_tree.dto.response;

import br.com.tiagoschermack.family_tree.domain.entity.FamilyTree;

public record FamilyTreeResponse(
        String id,
        String name,
        String description,
        boolean isPublic
) {
    public static FamilyTreeResponse of(FamilyTree tree) {
        return new FamilyTreeResponse(
                tree.getId(),
                tree.getName(),
                tree.getDescription(),
                tree.isPublic()
        );
    }
}
