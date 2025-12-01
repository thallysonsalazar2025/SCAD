package br.com.scad.scad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // todo criaçao de um bean do tipo SecurityFilterChain que injeta o HttpSecurity  e cria um form padrão e um basic
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable) //todo  desabilita a config para fazer a seguraça do front onde o front é obrigado a enviar um token para o back
//                .formLogin(configurer -> configurer.loginPage("login.html").successForwardUrl("home.html")) //todo habilida o form padrão e injeta um target para a pagina
//                .formLogin(Customizer.withDefaults()) //todo adiciona o form padrão
                .formLogin(configurer -> {
                    configurer.loginPage("/login").permitAll();
                })
                .httpBasic(Customizer.withDefaults()) //todo adiciona o autenticação basic sem ela autenticação nao funciona
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated()) // todo qualquer requisição deve estar autenticado
                .build();

    }
}
