package hhz.ktoeto.moneymanager.config;

import hhz.ktoeto.moneymanager.constant.CookieConstant;
import hhz.ktoeto.moneymanager.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final AuthService authService;

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
                authService.processAccessToken(accessToken.get(), refreshToken, response);
            } else if (refreshToken.isPresent()) {
                authService.processRefreshToken(refreshToken.get(), response);
            }
        } catch (Exception e) {
            log.error("Exception occurred, while filtering the request", e);
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private Optional<String> getCookieValue(List<Cookie> cookies, String name) {
        return cookies.stream()
                .filter(cookie -> Objects.equals(name, cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }
}
