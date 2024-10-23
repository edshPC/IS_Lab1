package com.edsh.is_lab1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Отключаем CSRF, если это необходимо для API
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/", "/index.html", "/static/**").permitAll() // Разрешаем доступ к главной странице и статическим ресурсам
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Указываем свою страницу авторизации
                        .permitAll() // Разрешаем доступ к странице авторизации
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            // Если пользователь не аутентифицирован, остаемся на главной странице
                            response.sendRedirect("/"); // Перенаправляем на главную страницу
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL для выхода
                        .permitAll() // Разрешаем доступ к выходу
                );

        return http.build();
    }
}
