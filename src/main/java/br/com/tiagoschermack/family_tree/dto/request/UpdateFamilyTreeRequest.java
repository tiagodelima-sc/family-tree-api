package br.com.tiagoschermack.family_tree.dto.request;

public record UpdateFamilyTreeRequest(
        String name,
        String description,
        Boolean isPublic
) {}
