package br.com.scad.scad.repository;

import br.com.scad.scad.domain.Client;
import br.com.scad.scad.dto.ClientResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByClientId(String clientId);
}
