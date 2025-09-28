package com.mottu.mapping.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailServiceImplementation userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/", "/index", "/index.html", "/css/**", "/js/**", "/images/**", "/static/**", "/*.css").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    // ADMIN pode acessar
                    .requestMatchers("/operator/**").hasAnyRole("OPERATOR")
                    .anyRequest().authenticated()
            )
                .formLogin(form -> form
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            boolean isAdmin = authentication.getAuthorities().stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
                            boolean isOperator = authentication.getAuthorities().stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_OPERATOR"));

                            if (isAdmin) {
                                response.sendRedirect("/admin/dashboard");
                            } else if (isOperator) {
                                response.sendRedirect("/operator/dashboard");
                            } else {
                                response.sendRedirect("/login?unauthorized");
                            }
                        })
                )

                .logout(logout -> logout
                    .permitAll()
                    .logoutSuccessUrl("/")
                )
                .exceptionHandling(exception -> exception
                    .accessDeniedPage("/403")
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}