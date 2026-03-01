package br.com.tiagoschermack.family_tree.dto.response;

import br.com.tiagoschermack.family_tree.domain.entity.User;

public record UserResponse(
        String id,
        String name,
        String email
) {
    public static UserResponse of(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
