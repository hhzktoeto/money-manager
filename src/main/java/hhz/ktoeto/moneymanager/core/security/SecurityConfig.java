package hhz.ktoeto.moneymanager.core.security;

import com.vaadin.flow.spring.security.VaadinAwareSecurityContextHolderStrategyConfiguration;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import hhz.ktoeto.moneymanager.ui.constant.Routes;
import hhz.ktoeto.moneymanager.feature.user.domain.UserService;
import hhz.ktoeto.moneymanager.feature.user.LoginRouteView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Import(VaadinAwareSecurityContextHolderStrategyConfiguration.class)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return username -> userService.getByUsername(username)
                .map(AppUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);

        return repository;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           UserDetailsService userDetailsService,
                                           PersistentTokenRepository persistentTokenRepository,
                                           @Value("${spring.security.remember-me.key}") String rememberMeKey,
                                           @Value("${spring.security.remember-me.cookie-name}") String rememberMeCookieName,
                                           @Value("${spring.security.remember-me.max-age}") Integer rememberMeMaxAge) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);

        return http
                .with(VaadinSecurityConfigurer.vaadin(), configurer -> configurer.loginView(LoginRouteView.class, Routes.LOGIN.getPath()))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/logo.png").permitAll())
                .requestCache(configurer -> configurer.requestCache(requestCache))
                .formLogin(configurer -> configurer
                        .loginPage(Routes.LOGIN.getPath())
                        .loginProcessingUrl(Routes.LOGIN.getPath())
                        .defaultSuccessUrl(Routes.HOME.getPath(), true)
                )
                .rememberMe(configurer -> configurer
                        .key(rememberMeKey)
                        .userDetailsService(userDetailsService)
                        .tokenRepository(persistentTokenRepository)
                        .rememberMeCookieName(rememberMeCookieName)
                        .tokenValiditySeconds(rememberMeMaxAge)
                        .useSecureCookie(true)
                        .alwaysRemember(true)
                )
                .securityContext(context -> context.requireExplicitSave(false))
                .build();
    }
}
