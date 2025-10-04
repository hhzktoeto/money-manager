package hhz.ktoeto.moneymanager.service;

import hhz.ktoeto.moneymanager.utils.CookieConstant;
import hhz.ktoeto.moneymanager.user.model.AuthResponse;
import hhz.ktoeto.moneymanager.user.model.User;
import hhz.ktoeto.moneymanager.user.service.TokenService;
import hhz.ktoeto.moneymanager.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenService tokenService;

    public void processAccessToken(String accessToken, Optional<String> refreshToken, HttpServletResponse response) {
        try {
            Long userId = tokenService.parseUserId(accessToken);
            this.authenticate(userId);
        } catch (ExpiredJwtException e) {
            refreshToken.ifPresent(token -> processRefreshToken(token, response));
        } catch (JwtException e) {
            log.warn("Invalid access token received", e);
        }
    }

    public void processRefreshToken(String refreshToken, HttpServletResponse response) {
        try {
            AuthResponse newTokens = userService.refresh(refreshToken);

            this.addCookie(response, CookieConstant.ACCESS_TOKEN, newTokens.accessToken(), CookieConstant.ACCESS_TOKEN_MAX_AGE);
            this.addCookie(response, CookieConstant.REFRESH_TOKEN, newTokens.refreshToken(), CookieConstant.REFRESH_TOKEN_MAX_AGE);

            Long userId = tokenService.parseUserId(newTokens.accessToken());
            this.authenticate(userId);
        } catch (Exception e) {
            log.warn("Invalid refresh token", e);
        }
    }

    private void authenticate(Long userId) {
        Optional<User> optionalUser = userService.getById(userId);
        if (optionalUser.isEmpty()) {
            log.warn("Attempt to authenticate user with id {}, which is not present in DB", userId);
            return;
        }
        User user = optionalUser.get();
        var auth = new UsernamePasswordAuthenticationToken(user, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
