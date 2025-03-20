package com.accenture.controller;

import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.accenture.service.dto.ClientRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody ClientRequestDto clientRequestDto){
        ClientResponseDto clientEnreg = clientService.ajouter(clientRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientEnreg.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    List<ClientResponseDto> trouverTous(){
        return clientService.trouverTous();
    }

    @GetMapping("/{id}")
    ClientResponseDto filterParId(@PathVariable int id){
        return clientService.filtrerParId(id);
    }
}
