package br.com.scad.scad.service.mapper;

import br.com.scad.scad.domain.Client;
import br.com.scad.scad.dto.ClientRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

@Mapper(componentModel = "spring")
public interface ClientMapper {
     @Mapping(target = "clientSecret", expression = "java(passwordEncoder.encode(client.clientSecret()))")
     Client toEntity(ClientRequest client, PasswordEncoder passwordEncoder);
}
