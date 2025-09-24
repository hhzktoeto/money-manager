package hhz.ktoeto.moneymanager.user.model;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
