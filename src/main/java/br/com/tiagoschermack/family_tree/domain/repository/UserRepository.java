package br.com.tiagoschermack.family_tree.domain.repository;

import br.com.tiagoschermack.family_tree.domain.entity.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById   (String id   );
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteById(String id);
}
