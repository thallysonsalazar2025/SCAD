package br.com.scad.scad.service;

import br.com.scad.scad.domain.Client;
import br.com.scad.scad.dto.ClientRequest;
import br.com.scad.scad.dto.ClientResponse;
import br.com.scad.scad.repository.ClientRepository;
import br.com.scad.scad.service.mapper.ClientMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository repository, ClientMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveClient(ClientRequest client){
        repository.save(mapper.toEntity(client,passwordEncoder));
    }

    public ClientResponse getClient(ClientRequest client){

        Client clientRequest = mapper.toEntity(client, passwordEncoder);
        return repository.findByClientId(clientRequest.getId().toString()).orElse(null);
    }
}
