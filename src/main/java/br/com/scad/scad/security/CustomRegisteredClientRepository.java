package br.com.scad.scad.security;

import br.com.scad.scad.domain.Client;
import br.com.scad.scad.dto.ClientRequest;
import br.com.scad.scad.dto.ClientResponse;
import br.com.scad.scad.service.ClientService;
import br.com.scad.scad.service.mapper.ClientMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
@Primary // Marks this as the primary implementation to avoid bean conflicts.
public class CustomRegisteredClientRepository implements RegisteredClientRepository {
    private final ClientService clientService;
    private final TokenSettings tokenSettings;
    private final ClientSettings clientSettingsConfig;
    private final ClientMapper mapper;


    public CustomRegisteredClientRepository(ClientService clientService, TokenSettings tokenSettings, ClientSettings clientSettingsConfig, ClientMapper mapper) {
        this.clientService = clientService;
        this.tokenSettings = tokenSettings;
        this.clientSettingsConfig = clientSettingsConfig;
        this.mapper = mapper;
    }


    @Override
    public void save(RegisteredClient registeredClient) {
        clientService.saveClient(mapper.toClientRequestMapper(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Optional<ClientResponse> clientResponseOptional = clientService.findByClientId(clientId);

        return clientResponseOptional.map(clientResponse -> {
            RegisteredClient.Builder builder = RegisteredClient.withId(clientResponse.id().toString())
                    .clientId(clientResponse.clientId())
                    .clientSecret(clientResponse.clientSecret())
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .tokenSettings(tokenSettings)
                    .clientSettings(clientSettingsConfig);

            if (clientResponse.scope() != null && !clientResponse.scope().isBlank()) {
                Arrays.stream(clientResponse.scope().split(" ")).forEach(builder::scope);
            }
            if (clientResponse.redirectUri() != null && !clientResponse.redirectUri().isBlank()) {
                Arrays.stream(clientResponse.redirectUri().split(",")).forEach(builder::redirectUri);
            }
            return builder.build();
        }).orElse(null);
    }
}
