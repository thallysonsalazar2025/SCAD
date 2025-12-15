package br.com.scad.scad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    @Order(2) // Define esta cadeia com prioridade menor
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // A MÁGICA ACONTECE AQUI: Esta cadeia só se aplica a URLs que começam com /api/
            .securityMatcher("/api/**")
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated()
            )
            // Configura o servidor para aceitar e validar tokens JWT (Bearer Token)
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    /**
     * Define o bean para o codificador de senhas.
     * Usamos o BCrypt, que é o padrão recomendado pelo Spring Security.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}