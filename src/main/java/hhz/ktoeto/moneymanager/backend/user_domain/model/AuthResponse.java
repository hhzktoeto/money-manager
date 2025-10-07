package hhz.ktoeto.moneymanager.backend.user_domain.model;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
