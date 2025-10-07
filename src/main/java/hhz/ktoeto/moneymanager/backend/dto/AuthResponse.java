package hhz.ktoeto.moneymanager.backend.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
