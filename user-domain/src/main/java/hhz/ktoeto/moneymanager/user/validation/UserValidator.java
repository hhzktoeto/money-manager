package hhz.ktoeto.moneymanager.user.validation;

import hhz.ktoeto.moneymanager.user.exception.InvalidRegisterRequestException;
import hhz.ktoeto.moneymanager.user.model.RegisterRequest;
import hhz.ktoeto.moneymanager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository repository;

    public void validateRegistration(RegisterRequest registerRequest) {
        String requestLogin = registerRequest.login();
        String requestEmail = registerRequest.email();
        String requestPhone = registerRequest.phone();

        Set<String> errorMessages = HashSet.newHashSet(3);
        if (repository.existsByLogin(requestLogin)) {
            errorMessages.add("User with login %s already exists".formatted(requestLogin));
        }
        if ((requestEmail != null && !requestEmail.isBlank()) && repository.existsByEmail(requestEmail)) {
            errorMessages.add("User with email %s already exists".formatted(requestEmail));
        }
        if ((requestPhone != null && !requestPhone.isBlank()) && repository.existsByPhone(requestPhone)) {
            errorMessages.add("User with phone %s already exists".formatted(requestPhone));
        }

        if (!errorMessages.isEmpty()) {
            throw new InvalidRegisterRequestException(String.join(", ", errorMessages));
        }
    }
}
