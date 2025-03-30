package com.home.auth_service.config;

import com.home.auth_service.service.JwtUtil;
import com.home.auth_service.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        .requestMatchers("/api/v1/security/register/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/security/register/user/**").hasAuthority("permission:write")
                        .requestMatchers("/api/v1/security/login").permitAll()
                        .requestMatchers("/api/v1/security/logout").permitAll()
                        .requestMatchers("/api/v1/security/recover-password").permitAll()
                        .anyRequest().authenticated())
                .logout(logout -> logout.logoutUrl("/api/v1/security/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            addTokenToBlackList(request);
                            response.sendRedirect("/api/v1/security/login");
                            response.setStatus(HttpServletResponse.SC_OK);
                        }))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void addTokenToBlackList(HttpServletRequest request) {
        if (request.getHeader("Authorization") != null) {
            String token = request.getHeader("Authorization").substring(7);
            String username = JwtUtil.extractUsername(token);
            redisService.putTokenBlackListByUsername(username, token);
            log.info("Added token to blacklist. Username: {}", username);
        } else {
            log.warn(
                    "No Authorization header found in request. Bad added token to blacklist. Request: {}",
                    request.getHeaderNames()
            );
        }
    }

}
