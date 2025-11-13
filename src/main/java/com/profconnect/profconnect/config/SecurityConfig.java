package com.profconnect.profconnect.config;

import org.springframework.http.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                // ✅ ENABLE CORS HERE (actual rules are in CorsConfig)
                .cors(cors -> {})

                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // ✅ Allow CORS PREFLIGHT REQUESTS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ✅ Allow homepage
                        .requestMatchers(HttpMethod.GET, "/").permitAll()

                        // Public endpoints
                        .requestMatchers(
                                "/api/professors/register",
                                "/api/professors/login",
                                "/api/students/register",
                                "/api/students/login"
                        ).permitAll()

                        // Allow static uploads
                        .requestMatchers("/uploads/**").permitAll()

                        // Everything else requires JWT
                        .anyRequest().authenticated()
                );

        // Keep original logic — don't change
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
