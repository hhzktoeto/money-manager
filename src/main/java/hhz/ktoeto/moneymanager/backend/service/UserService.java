package hhz.ktoeto.moneymanager.backend.service;

import hhz.ktoeto.moneymanager.backend.dto.RegisterRequest;
import hhz.ktoeto.moneymanager.backend.entity.User;
import hhz.ktoeto.moneymanager.backend.repository.UserRepository;
import hhz.ktoeto.moneymanager.backend.validation.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final UserValidator validator;

    public Optional<User> getByUsername(String username) {
        return repository.findByLogin(username);
    }

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
}
