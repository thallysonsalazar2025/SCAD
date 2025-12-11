package br.com.scad.scad.dto;


import java.util.UUID;

public record ClientResponse(
        UUID id,
        String clientId,
        String redirectUri,
        String clientSecret,
        String scope

) {}
