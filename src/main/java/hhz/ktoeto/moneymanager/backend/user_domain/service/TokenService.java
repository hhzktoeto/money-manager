package hhz.ktoeto.moneymanager.backend.user_domain.service;

import hhz.ktoeto.moneymanager.backend.user_domain.exception.RefreshTokenNotFoundException;
import hhz.ktoeto.moneymanager.backend.user_domain.repository.TokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository repository;
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Long userId, Duration expiration) {
        log.info("Generating new token");
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration.toMillis());

        String token = Jwts.builder()
                .subject(userId.toString())
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(secretKey())
                .compact();

        log.info("Token generated");
        return token;
    }

    public void saveRefreshToken(String token, long userId, Duration expiration) {
        log.info("Saving refresh token");
        repository.save(token, userId, expiration);
    }

    public Long parseUserId(String refreshToken) {
        return Long.parseLong(Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload()
                .getSubject());
    }

    public Long getUserId(String refreshToken) {
        return repository.get(refreshToken)
                .orElseThrow(() -> new RefreshTokenNotFoundException("No refresh token found"));
    }

    public void deleteToken(String refreshToken) {
        repository.delete(refreshToken);
    }

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
