package br.com.tiagoschermack.family_tree.dto.response;

public record TokenResponse(
        String token,
        String tokenType,
        UserResponse user
) {}
