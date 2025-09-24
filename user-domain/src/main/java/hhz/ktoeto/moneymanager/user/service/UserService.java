package hhz.ktoeto.moneymanager.user.service;

import hhz.ktoeto.moneymanager.user.exception.ImBeingHackedOrImDumb;
import hhz.ktoeto.moneymanager.user.exception.UserNotFoundException;
import hhz.ktoeto.moneymanager.user.exception.WrongPasswordException;
import hhz.ktoeto.moneymanager.user.model.AuthResponse;
import hhz.ktoeto.moneymanager.user.model.LoginRequest;
import hhz.ktoeto.moneymanager.user.model.RegisterRequest;
import hhz.ktoeto.moneymanager.user.model.User;
import hhz.ktoeto.moneymanager.user.repository.UserRepository;
import hhz.ktoeto.moneymanager.user.validation.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final TokenService tokenService;
    private final UserValidator validator;

    @Transactional
    public User register(RegisterRequest request) {
        log.info("Processing register request");
        validator.validateRegistration(request);

        User user = new User();
        user.setLogin(request.login());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setPhone(request.phone());

        User savedUser = repository.save(user);
        log.info("User was created");
        return savedUser;
    }

    public AuthResponse login(LoginRequest request) {
        log.info("Processing login request");
        User user = repository.findByLogin(request.login())
                .orElseThrow(() -> new UserNotFoundException("User with login %s doesn't exist".formatted(request.login())));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new WrongPasswordException();
        }

        AuthResponse response = generateTokens(user.getId());
        log.info("Successfully logged in");

        return response;
    }

    public AuthResponse refresh(String refreshToken) {
        Long repoUserId = tokenService.getUserId(refreshToken);
        Long tokenUserId = tokenService.parseUserId(refreshToken);

        if (!Objects.equals(repoUserId, tokenUserId)) {
            throw new ImBeingHackedOrImDumb("Refresh token user ID does not match");
        }
        if (!repository.existsById(repoUserId)) {
            throw new UserNotFoundException("User with id %d doesn't exist".formatted(repoUserId));
        }

        AuthResponse response = generateTokens(repoUserId);
        tokenService.deleteToken(refreshToken);
        log.info("Successfully refreshed tokens");

        return response;
    }

    private AuthResponse generateTokens(Long userId) {
        String accessToken = tokenService.generateToken(userId, Duration.ofMinutes(10));
        String refreshToken = tokenService.generateToken(userId, Duration.ofDays(7));
        tokenService.saveRefreshToken(refreshToken, userId, Duration.ofDays(7));

        return new AuthResponse(accessToken, refreshToken);
    }
}
