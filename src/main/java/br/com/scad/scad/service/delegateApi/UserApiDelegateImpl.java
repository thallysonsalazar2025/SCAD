package br.com.scad.scad.service.delegateApi;

import br.com.scad.scad.domain.UserDomain;
import br.com.scad.scad.generated.api.UsersApiDelegate;
import br.com.scad.scad.generated.model.UserRegistrationRequest;
import br.com.scad.scad.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class UserApiDelegateImpl implements UsersApiDelegate {

    private final UserService userService;

    public UserApiDelegateImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Void> registerUser(UserRegistrationRequest userRegistrationRequest) {
        UserDomain savedUser = userService.createNewUserIn(userRegistrationRequest);
        // Constrói a URI para o novo recurso criado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() // Pega a URL atual (ex: /api/users)
                .path("/{id}")       // Adiciona o path do ID
                .buildAndExpand(savedUser.getId()) // Substitui {id} pelo ID do usuário salvo
                .toUri();

        // Retorna 201 Created com o header "Location" apontando para a nova URI
        return ResponseEntity.created(location).build();
    }

}
