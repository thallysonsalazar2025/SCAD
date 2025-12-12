package br.com.scad.scad.config;

import org.junit.jupiter.api.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Duration;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration {

    @Bean
    @Order(1) //todo qualifica esse bean para ser priorizado na hora de criar e gerenciar pelo spring Security
    public SecurityFilterChain serverSecurityFilterChain(HttpSecurity http) throws Exception {
        //todo cria a configuração de Oauth2 do ResourceServer
        http.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests.anyRequest().authenticated()
        ) //todo todas as requisições precisam estar autenticadas e essa linha faz isso
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(withDefaults()))
                .formLogin(configurer -> configurer.loginPage("/login"))
                .oauth2Login(withDefaults()
                        ); // todo adiciona o suporte ao login via oauth2 via open id connect (oidc)

        return http.build();
    }
    //todo cria um Bean que faz a critografia da senha atraves da,
    // interface PasswordEncoder e retorna um obj do tipo BCryptPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                .accessTokenTimeToLive(Duration.ofMinutes(60))
                .build();
    }
    @Bean
    public ClientSettings clientSettings(){
        return ClientSettings.builder()
                .requireAuthorizationConsent(false)
                .build();
    }
}
