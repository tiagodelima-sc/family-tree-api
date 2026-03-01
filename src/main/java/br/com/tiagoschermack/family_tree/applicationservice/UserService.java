package br.com.tiagoschermack.family_tree.applicationservice;

import br.com.tiagoschermack.family_tree.domain.entity.User;
import br.com.tiagoschermack.family_tree.domain.repository.UserRepository;
import br.com.tiagoschermack.family_tree.dto.request.UpdateUserRequest;
import br.com.tiagoschermack.family_tree.dto.response.UserResponse;
import br.com.tiagoschermack.family_tree.shared.exception.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse getUser(String userId) {
        return UserResponse.of(findUserById(userId));
    }

    public UserResponse updateUser(String userId, UpdateUserRequest userRequest) {
        User existingUser = findUserById(userId);

        if (userRequest.name() != null)
            existingUser.setName(userRequest.name());

        if (userRequest.email() != null)
            existingUser.setEmail(userRequest.email());

        if (userRequest.password() != null)
            existingUser.setPassword(passwordEncoder.encode(userRequest.password()));

        return UserResponse.of(userRepository.save(existingUser));
    }

    public void deleteUser(String userId) {
        User existingUser = findUserById(userId);
        existingUser.setDeleted(true);

        userRepository.save(existingUser);
    }

    private User findUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("O usuário não foi encontrado"));
    }
}
