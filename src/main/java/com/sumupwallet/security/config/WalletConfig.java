package com.sumupwallet.security.config;

import com.sumupwallet.security.jwt.AuthTokenFilter;
import com.sumupwallet.security.jwt.JwtAuthEntryPoint;
import com.sumupwallet.security.jwt.JwtUtils;
import com.sumupwallet.security.user.WalletUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class WalletConfig {

    private final WalletUserDetailsService userDetailsService;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtUtils jwtUtils;

    private static final List<String> SECURED_URLS =
            List.of("/api/v1/wallets/**","api/v1/users/update/**",
                    "api/v1/users/by-email/**",
                    "api/v1/users/by-id/**");

    @Autowired
    public WalletConfig(WalletUserDetailsService userDetailsService, JwtAuthEntryPoint jwtAuthEntryPoint, JwtUtils jwtUtils) {

        this.userDetailsService = userDetailsService;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authTokenFilter() {

        return new AuthTokenFilter(jwtUtils, userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/login").permitAll()
                        .requestMatchers(SECURED_URLS.toArray(String[]::new)).authenticated()
                        .anyRequest().permitAll());

        http.authenticationProvider(daoAuthenticationProvider());
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
