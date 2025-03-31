package com.home.auth_service.config;

import com.home.auth_service.service.RedisService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final RedisService redisService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/security/register/admin").hasRole("ADMIN")
                        .requestMatchers("/api/v1/security/register/user").hasAuthority("permission:write")
                        .requestMatchers("/api/v1/security/login").permitAll()
                        .requestMatchers("/api/v1/security/logout").permitAll()
                        .requestMatchers("/api/v1/security/recover-password").permitAll()
                        .anyRequest().authenticated())
                .logout(logout -> logout.logoutUrl("/api/v1/security/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {

                            redisService.addTokenToBlackList(request);

                            response.sendRedirect("/api/v1/security/login");
                            response.setStatus(HttpServletResponse.SC_OK);
                        }))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
