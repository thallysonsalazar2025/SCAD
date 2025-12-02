package br.com.scad.scad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // todo criaçao de um bean do tipo SecurityFilterChain que injeta o HttpSecurity  e cria um form padrão e um basic
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable) //todo  desabilita a config para fazer a seguraça do front onde o front é obrigado a enviar um token para o back
//                .formLogin(configurer -> configurer.loginPage("login.html").successForwardUrl("home.html")) //todo habilida o form padrão e injeta um target para a pagina
//                .formLogin(Customizer.withDefaults()) //todo adiciona o form padrão
                .formLogin(configurer -> {
                    configurer.loginPage("/login").permitAll();
                })
                //todo estou setando as roles de cada usuario na aplicação atraves do método authorizeHttpRequests da classe: HttpSecurity
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers(HttpMethod.POST, "/autores/**").hasRole("ADMIN");
                    authorizeRequests.requestMatchers(HttpMethod.PUT, "/autores/**").hasRole("ADMIN");
                    authorizeRequests.requestMatchers(HttpMethod.DELETE, "/autores/**").hasRole("ADMIN");
                    authorizeRequests.requestMatchers(HttpMethod.POST, "/books/**").hasRole("ADMIN");
                    authorizeRequests.requestMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN");
                    authorizeRequests.requestMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN");

                    //todo setando autorização para o usuario ter uma permissão especifica utilizando o método hasAuthority
                    authorizeRequests.requestMatchers(HttpMethod.POST, "/books/**").hasAuthority("CADASTRO_USUARIO");
                    //todo adiciona o autenticação basic sem ela autenticação nao funciona
                    authorizeRequests.anyRequest().authenticated();
                }).build();


    }

    //todo cria um Bean que faz a critografia da senha atraves da,
    // interface PasswordEncoder e retorna um obj do tipo BCryptPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    //todo Cria um bean para criar usuarios em mémoria e injeta o PasswordEncoder para capturar o password e criptografar a senha
    // repositorio de usuarios em memoria!***
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        UserDetails user1 = User.builder()
                .username("usuario")
                .password(encoder.encode("123"))
                .roles("USER")
                .build();

        UserDetails user2 = User.builder()
                .username("admin")
                .password(encoder.encode("456"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}
