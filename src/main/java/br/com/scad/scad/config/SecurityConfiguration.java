package br.com.scad.scad.config;

import br.com.scad.scad.security.LoginSocialSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
//todo responsavél por habilitar o controle de regras nos controllers
public class SecurityConfiguration {

    // todo criaçao de um bean do tipo SecurityFilterChain que injeta o HttpSecurity  e cria um form padrão e um basic
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSuccessHandler successHandler) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable) //todo  desabilita a config para fazer a seguraça do front onde o front é obrigado a enviar um token para o back
                .formLogin(configurer -> {
                    configurer.loginPage("/login").permitAll();
                })
                //todo estou setando as roles de cada usuario na aplicação atraves do método authorizeHttpRequests da classe: HttpSecurity
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers("/login").permitAll();
                    authorizeRequests.requestMatchers(HttpMethod.POST, "/users/**").permitAll();
                    authorizeRequests.anyRequest().authenticated();

                })
                .oauth2ResourceServer(oauth2rs -> oauth2rs.jwt(Customizer.withDefaults()))
                .oauth2Login(ouath2 -> ouath2
                        .loginPage("/login")
                        .successHandler(successHandler))
                .build();
    }


    //todo cria um bean para setar o prexisso da authorenty sem nenhum valor anteriormente ele usava o valor ROLE_
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
    //todo Configura o token JWT e seta o prefixo SCOPE sem nenhum valor
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix(""); // todo set para definir o prefixo que vem do token jtw
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return  jwtConverter;
    }


}
