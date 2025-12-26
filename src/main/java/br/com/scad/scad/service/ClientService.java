package br.com.scad.scad.service;

import br.com.scad.scad.domain.Client;
import br.com.scad.scad.dto.ClientRequest;
import br.com.scad.scad.dto.ClientResponse;
import br.com.scad.scad.repository.ClientRepository;
import br.com.scad.scad.service.mapper.ClientMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
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
        log.info("Save a new Client"+ client);
        repository.save(mapper.toEntity(client,passwordEncoder));
    }

    @Transactional(readOnly = true)
    public Optional<ClientResponse> findByClientId(String clientId) {
        return repository.findByClientId(clientId).map(mapper::toResponse);
    }
}
