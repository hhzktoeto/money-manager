package hhz.ktoeto.moneymanager.backend.user_domain.model;

public record LoginRequest(
        String login,
        String password
) {
}
