package br.com.scad.scad.config;

import br.com.scad.scad.security.CustomUserDetailsService;
import br.com.scad.scad.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true) //todo responsavél por habilitar o controle de regras nos controllers
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
                    authorizeRequests.requestMatchers("/api/v1/login").permitAll();
                    authorizeRequests.requestMatchers(HttpMethod.POST, "/users/**").permitAll();
                    authorizeRequests.anyRequest().authenticated();

//                    authorizeRequests.requestMatchers(HttpMethod.POST, "/autores/**").hasRole("ADMIN");
//                    authorizeRequests.requestMatchers(HttpMethod.PUT, "/autores/**").hasRole("ADMIN");
//                    authorizeRequests.requestMatchers(HttpMethod.POST,"/users/**").hasRole("ADMIN");
//                    authorizeRequests.requestMatchers(HttpMethod.DELETE, "/autores/**").hasRole("ADMIN");
//                    authorizeRequests.requestMatchers(HttpMethod.POST, "/books/**").hasRole("ADMIN");
//                    authorizeRequests.requestMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN");
//                    authorizeRequests.requestMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN");
                    //todo setando autorização para o usuario ter uma permissão especifica utilizando o método hasAuthority
//                    authorizeRequests.requestMatchers(HttpMethod.POST, "/books/**").hasAuthority("CADASTRO_USUARIO");
                    //todo adiciona o autenticação basic sem ela autenticação nao funciona
                }).build();


    }

    //todo cria um Bean que faz a critografia da senha atraves da,
    // interface PasswordEncoder e retorna um obj do tipo BCryptPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    //todo retorna a configuração da CustomUserDetailsService onde eu crio a configuração de acesso utilizando
//    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        //todo Cria um bean para criar usuarios em mémoria e injeta o PasswordEncoder para capturar o password e criptografar a senha
        // repositorio de usuarios em memoria!***

//        UserDetails user1 = User.builder()
//                .username("usuario")
//                .password(encoder.encode("123"))
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User.builder()
//                .username("admin")
//                .password(encoder.encode("456"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user1, user2);

        return new CustomUserDetailsService(userService);
    }
    //todo cria um bean para setar o prexisso da authorenty sem nenhum valor anteriormente ele usava o valor ROLE_
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new  GrantedAuthorityDefaults("");
    }


}
