package br.com.tiagoschermack.family_tree.dto.response;

import java.util.List;

public record ErrorResponse(
        String message,
        String path,
        List<FieldErrorResponse> fieldErrors
) {}
