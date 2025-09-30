package hhz.ktoeto.moneymanager.utils;

import com.vaadin.flow.server.VaadinService;
import hhz.ktoeto.moneymanager.constant.CookieConstant;
import jakarta.servlet.http.Cookie;

import java.util.List;

public final class CookieUtils {

    private CookieUtils() {}

    public static void addTokensToClientsCookies(String accessToken, String refreshToken) {
        List<Cookie> cookies = List.of(
                buildCookie(CookieConstant.ACCESS_TOKEN, accessToken, CookieConstant.ACCESS_TOKEN_MAX_AGE),
                buildCookie(CookieConstant.REFRESH_TOKEN, refreshToken, CookieConstant.REFRESH_TOKEN_MAX_AGE)
        );

        cookies.forEach(cookie -> VaadinService.getCurrentResponse().addCookie(cookie));
    }

    private static Cookie buildCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        return cookie;
    }
}
