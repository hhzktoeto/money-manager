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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]);
            Optional<String> accessToken = Arrays.stream(cookies)
                    .filter(cookie -> Objects.equals(CookieConstant.ACCESS_TOKEN, cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst();
            if (accessToken.isPresent()) {
                try {
                    Long userId = tokenService.parseUserId(accessToken.get());
                    userService.getById(userId).ifPresent(user -> {
                        var auth = new UsernamePasswordAuthenticationToken(user, null, List.of());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    });
                } catch (ExpiredJwtException e) {
                    Optional<String> refreshToken = Arrays.stream(cookies)
                            .filter(cookie -> Objects.equals(CookieConstant.REFRESH_TOKEN, cookie.getName()))
                            .map(Cookie::getValue)
                            .findFirst();
                    if (refreshToken.isPresent()) {
                        try {
                            AuthResponse newTokens = userService.refresh(refreshToken.get());
                            addCookie(response, CookieConstant.ACCESS_TOKEN, newTokens.accessToken(), CookieConstant.ACCESS_TOKEN_MAX_AGE);
                            addCookie(response, CookieConstant.REFRESH_TOKEN, newTokens.refreshToken(), CookieConstant.REFRESH_TOKEN_MAX_AGE);

                            Long userId = tokenService.parseUserId(newTokens.accessToken());
                            userService.getById(userId).ifPresent(user -> {
                                var auth = new UsernamePasswordAuthenticationToken(user, null, List.of());
                                SecurityContextHolder.getContext().setAuthentication(auth);
                            });
                        } catch (Exception ex) {
                            log.warn("Invalid refresh token", ex);
                        }
                    }
                } catch (JwtException e) {
                    log.warn("Invalid access token", e);
                }
            }
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
