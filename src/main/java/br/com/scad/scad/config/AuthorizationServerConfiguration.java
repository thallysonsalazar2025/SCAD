package br.com.scad.scad.config;


import br.com.scad.scad.security.CustomAuthentication;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Configuration
public class AuthorizationServerConfiguration {

    // Matcher local para os endpoints do Authorization Server (reutilizável)
    // Não inclui "/oauth2/authorization/**" para não bloquear OAuth2 Client (ex: Google login)
    private static RequestMatcher authorizationServerEndpointsMatcher() {
        final String[] endpoints = {
                "/.well-known",
                "/.well-known/openid-configuration",
                "/oauth2/authorize",
                "/oauth2/token",
                "/oauth2/introspect",
                "/oauth2/revoke",
                "/.well-known/**",
                "/oauth2/jwks",
                "/userinfo"
        };

        return request -> {
            String uri = request.getRequestURI();
            for (String e : endpoints) {
                if (uri.equals(e) || uri.startsWith(e + "/")) {
                    return true;
                }
            }
            return false;
        };
    }

    // 1. CADEIA DE FILTROS PARA OS ENDPOINTS DO AUTHORIZATION SERVER
    @Bean
    @Order(1) // Define esta cadeia como a de maior prioridade
    @SuppressWarnings("deprecation")
    public SecurityFilterChain serverSecurityFilterChain(HttpSecurity http) throws Exception {
        // Garantir que esta cadeia só trate os endpoints do Authorization Server
        http.securityMatcher(authorizationServerEndpointsMatcher());

        // Aplica a configuração padrão do Authorization Server (para endpoints específicos)
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        // Habilita o suporte ao OpenID Connect 1.0
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        // Configura o redirecionamento para a página de login se o usuário não estiver autenticado
        http.exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
        );

        return http.build();
    }

    // 2. CADEIA DE FILTROS PADRÃO PARA A APLICAÇÃO (API E LOGIN)
    @Bean
    @Order(2)
    public SecurityFilterChain publicSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher(
                        "/login",
                        "/setup/**", // LIBERADO O ENDPOINT DE SETUP
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger/scad.yaml",
                        "/css/**",
                        "/js/**",
                        "/images/**"
                )
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/setup/**")) // DESABILITA CSRF PARA O SETUP
                .formLogin(form -> form.loginPage("/login"))
                .oauth2Login(oauth2 -> oauth2.loginPage("/login"));

        return http.build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher(new NegatedRequestMatcher(authorizationServerEndpointsMatcher()))
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }


    /**
     * Define as configurações para os tokens gerados (ex: tempo de vida).
     */
    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                //todo aces_token é o tokn utilizado nas requisições
                .accessTokenTimeToLive(Duration.ofMinutes(60)) // Access token válido por 60 minutos
                //todo token para renovar o acess o tempo dele é maior que o acess
                .refreshTokenTimeToLive(Duration.ofDays(90)) // a seção é extendida por mais 90 minutos
                .build();
    }

    /**
     * Define as configurações para os clients registrados (ex: tela de consentimento).
     */
    @Bean
    public ClientSettings clientSettings() {
        return ClientSettings.builder()
                .requireAuthorizationConsent(false) // Desabilita a tela de consentimento para o client
                .build();
    }

    // 3. BEANS ESSENCIAIS PARA O AUTHORIZATION SERVER
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();

        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("http://localhost:8080")
                .build();
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return context -> {
            var principal = context.getPrincipal();
            if (principal instanceof CustomAuthentication customAuthentication) {
                OAuth2TokenType typeToken = context.getTokenType();
                if (OAuth2TokenType.ACCESS_TOKEN.equals(typeToken)) {
                    Collection<GrantedAuthority> authorities =
                            customAuthentication.getAuthorities();

                    List<String> listAuthorities =
                            authorities.stream().map(GrantedAuthority::getAuthority)
                            .toList();

                    context.getClaims()
                            .claim("authorities", listAuthorities)
                            .claim("name", principal.getName())
                            .claim("login", (customAuthentication.getUserRequest()));

                }
            }
        };
    }



}
