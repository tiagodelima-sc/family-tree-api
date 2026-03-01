package br.com.tiagoschermack.family_tree.applicationservice;

import br.com.tiagoschermack.family_tree.domain.entity.User;
import br.com.tiagoschermack.family_tree.domain.repository.UserRepository;
import br.com.tiagoschermack.family_tree.dto.request.CreateUserRequest;
import br.com.tiagoschermack.family_tree.dto.request.LoginUserRequest;
import br.com.tiagoschermack.family_tree.dto.response.TokenResponse;
import br.com.tiagoschermack.family_tree.dto.response.UserResponse;
import br.com.tiagoschermack.family_tree.shared.exception.DuplicateException;
import br.com.tiagoschermack.family_tree.shared.exception.UnauthorizedException;
import br.com.tiagoschermack.family_tree.shared.security.JwtService;
import br.com.tiagoschermack.family_tree.shared.security.UserAuthenticated;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService      = jwtService;
    }

    public UserResponse register(CreateUserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.email()))
            throw new DuplicateException("Já existe um usuário cadastrado com esse e-mail");

        User user = new User(userRequest.name(), userRequest.email(), passwordEncoder.encode(userRequest.password()));
        return UserResponse.of(userRepository.save(user));
    }

    public TokenResponse login(LoginUserRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new UnauthorizedException("Senha ou usuário incorreto"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword()))
            throw new UnauthorizedException("Senha ou usuário incorreto");

        return new TokenResponse(jwtService.generateToken(new UserAuthenticated(user)), "Bearer", UserResponse.of(user));
    }
}
