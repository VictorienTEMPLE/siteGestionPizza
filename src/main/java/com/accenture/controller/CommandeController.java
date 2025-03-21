package com.accenture.controller;


import com.accenture.service.CommandeService;
import com.accenture.service.CommandeServiceImpl;
import com.accenture.service.PizzaService;
import com.accenture.service.dto.CommandeRequestDto;
import com.accenture.service.dto.CommandeResponseDto;
import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.service.dto.PizzaResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/commandes")
@Tag(name="Commandes", description = "Gestion des commandes")
public class CommandeController {
    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }


    @PostMapping
    @Operation(summary = "Ajouter une nouvelle commande", description ="Ajoute une nouvelle commande à la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Commande créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<CommandeResponseDto> ajouter(@RequestBody CommandeRequestDto commandeRequestDto) {
        CommandeResponseDto commandeResponseDtoAjoute = commandeService.ajouter(commandeRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(commandeResponseDtoAjoute.id())
                .toUri();
        log.info("Pizza ajoutée avec succès : {}", commandeRequestDto);
        return ResponseEntity.created(location).body(commandeResponseDtoAjoute);
    }



}
