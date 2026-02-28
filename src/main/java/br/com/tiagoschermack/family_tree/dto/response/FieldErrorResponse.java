package br.com.tiagoschermack.family_tree.dto.response;

public record FieldErrorResponse(
        String field,
        String message
) {}
