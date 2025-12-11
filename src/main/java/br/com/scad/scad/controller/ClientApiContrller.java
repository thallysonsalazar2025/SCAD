package br.com.scad.scad.controller;

import br.com.scad.scad.dto.ClientRequest;
import br.com.scad.scad.dto.ClientResponse;
import br.com.scad.scad.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientApiContrller {
    private final ClientService service;

    public ClientApiContrller(ClientService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void createClient(@RequestBody ClientRequest client){
         service.saveClient(client);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse getClient(@RequestBody ClientRequest client){
        return service.getClient(client);
    }
}
