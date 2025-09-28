package hhz.ktoeto.moneymanager.config;

import hhz.ktoeto.moneymanager.constant.CookieConstant;
import hhz.ktoeto.moneymanager.user.model.AuthResponse;
import hhz.ktoeto.moneymanager.user.service.TokenService;
import hhz.ktoeto.moneymanager.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            List<Cookie> authCookies = Arrays.stream(request.getCookies())
                    .filter(cookie ->
                            Objects.equals(CookieConstant.ACCESS_TOKEN, cookie.getName())
                            || Objects.equals(CookieConstant.REFRESH_TOKEN, cookie.getName())
                    )
                    .toList();

            if (authCookies.isEmpty()) {
                return;
            }

            Optional<String> accessToken = getCookieValue(authCookies, CookieConstant.ACCESS_TOKEN);
            Optional<String> refreshToken = getCookieValue(authCookies, CookieConstant.REFRESH_TOKEN);

            if (accessToken.isPresent()) {
                processAccessToken(accessToken.get(), refreshToken, response);
            } else if (refreshToken.isPresent()) {
                processRefreshToken(refreshToken.get(), response);
            }
        } catch (Exception e) {
            log.error("Exception occurred, while filtering the request", e);
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private void processAccessToken(String accessToken, Optional<String> refreshToken, HttpServletResponse response) {
        try {
            Long userId = tokenService.parseUserId(accessToken);
            authenticate(userId);
        } catch (ExpiredJwtException e) {
            refreshToken.ifPresent(s -> processRefreshToken(s, response));
        } catch (JwtException e) {
            log.warn("Invalid access token received", e);
        }
    }

    private void processRefreshToken(String token, HttpServletResponse response) {
        try {
            AuthResponse newTokens = userService.refresh(token);

            addCookie(response, CookieConstant.ACCESS_TOKEN, newTokens.accessToken(), CookieConstant.ACCESS_TOKEN_MAX_AGE);
            addCookie(response, CookieConstant.REFRESH_TOKEN, newTokens.refreshToken(), CookieConstant.REFRESH_TOKEN_MAX_AGE);

            Long userId = tokenService.parseUserId(newTokens.accessToken());
            userService.getById(userId).ifPresent(user -> {
                var auth = new UsernamePasswordAuthenticationToken(user, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            });
        } catch (Exception e) {
            log.warn("Invalid refresh token", e);
        }
    }

    private void authenticate(Long userId) {
        userService.getById(userId).ifPresent(user -> {
            var auth = new UsernamePasswordAuthenticationToken(user, null, List.of());
            SecurityContextHolder.getContext().setAuthentication(auth);
        });
    }
    private Optional<String> getCookieValue(List<Cookie> cookies, String name) {
        return cookies.stream()
                .filter(cookie -> Objects.equals(name, cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
