package br.com.scad.scad.security;

import br.com.scad.scad.dto.ClientRequest;
import br.com.scad.scad.service.ClientService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomRegisteredClientRepository implements RegisteredClientRepository {
    private final ClientService clientService;

    public CustomRegisteredClientRepository(ClientService clientService) {
        this.clientService = clientService;
    }


    @Override
    public void save(RegisteredClient registeredClient) {}

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        var request = new ClientRequest(clientId, null, null, null);

        // todo vai na base buscar um client registrado se não encontrar retorna null se encontrar ele
        var client = clientService.getClient(request);
        if (client == null){
            return null;
        }

        //todo seta o  RegisteredClient que envia para o Authorization Server o client registrado
        return RegisteredClient
                .withId(client.id().toString())
                .clientId(client.clientId())
                .clientSecret(client.clientSecret())
                .redirectUri(client.redirectUri())
                .scope(client.scope())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .tokenSettings()
                .build();
    }
}
