package br.com.scad.scad.security;

import br.com.scad.scad.dto.ClientRequest;
import br.com.scad.scad.service.ClientService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

@Component
public class CustomRegisteredClientRepository implements RegisteredClientRepository {
    private final ClientService clientService;
    private final TokenSettings tokenSettings;
    private final ClientSettings clientSettingsConfig;
    private final PasswordEncoder passwordEncoder;


    public CustomRegisteredClientRepository(ClientService clientService, TokenSettings tokenSettings, ClientSettings clientSettingsConfig, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.tokenSettings = tokenSettings;
        this.clientSettingsConfig = clientSettingsConfig;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void save(RegisteredClient registeredClient) {
    }

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        var request = new ClientRequest(clientId, null, null, null);

        // todo vai na base buscar um client registrado se não encontrar retorna null se encontrar ele
        var client = clientService.getClient(request);
        if (client == null) {
            return null;
        }

        return RegisteredClient
                //todo Inclui valores de parametros do client registrado
                .withId(client.id().toString())
                .clientId(client.clientId())
                .clientSecret(passwordEncoder.encode(client.clientSecret()))
                .redirectUri(client.redirectUri())
                .scope(client.scope())
                //todo Inclui configuração no client referente ao tipo de autenticação do client
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                //todo injeta a configuração da classe AuthorizationServerConfiguration token dura 60 minutos
                .tokenSettings(tokenSettings)
                //todo injeta a configuração da classe AuthorizationServerConfiguration desabilita tela consentimento do google
                .clientSettings(clientSettingsConfig)
                .build();
    }
}
