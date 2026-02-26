package br.com.tiagoschermack.family_tree.dto.request;

public record UpdateUserRequest(
        String name,
        String email,
        String password
) {}
