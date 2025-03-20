package com.accenture.controller;

import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.dto.PizzaResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
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
