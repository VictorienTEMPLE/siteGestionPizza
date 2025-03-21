package com.accenture.controller;

import com.accenture.service.PizzaService;
import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.service.dto.PizzaResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pizzas")
@Tag(name="Pizzas", description = "Gestion des pizzas")
public class PizzaController {

    private final PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @PostMapping
    @Operation(summary = "Ajouter une nouvelle pizza", description ="Ajoute une nouvelle pizza à la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pizza créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<PizzaResponseDto> ajouter(@RequestBody PizzaRequestDto pizzaRequestDto) {
        PizzaResponseDto pizzaResponseDtoAjoute = pizzaService.ajouter(pizzaRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(pizzaResponseDtoAjoute.id())
                .toUri();
        log.info("Pizza ajoutée avec succès : {}", pizzaRequestDto);
        return ResponseEntity.created(location).body(pizzaResponseDtoAjoute);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une pizza", description ="Supprime une pizza de la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pizza supprimée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<PizzaResponseDto> supprimer(@PathVariable int id){
        PizzaResponseDto responseDto = pizzaService.supprimer(id);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "modifie une pizza", description ="Modifie une pizza dans la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza modifiée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<PizzaResponseDto> modifier(@PathVariable int id, @RequestBody PizzaRequestDto pizzaRequestDto){
        log.info("Pizza à modifée : {}",pizzaRequestDto);
        PizzaResponseDto responseDto = pizzaService.modifier(id,pizzaRequestDto);
        log.info("Pizza modifée : {}",responseDto);
        return ResponseEntity.ok(responseDto);
    }
    @Operation(summary = "Lister pizza", description ="Affiche les pizzas dans la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza modifiée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @GetMapping
    List<PizzaResponseDto> trouverTous(){
        log.info("Liste des pizza récupérées, Nombre de pizzas : {}", pizzaService.trouverTous().size());
        return pizzaService.trouverTous();

    }

    @GetMapping("/{id}")
    @Operation(summary = "filtre une pizza par ID", description ="trouve une pizza dans la base de données par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza trouvée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    PizzaResponseDto filtrerParId(@PathVariable int id){
        log.info("Pizza trouvée : {}", pizzaService.filtrerParId(id));
        return pizzaService.filtrerParId(id);
    }

    @GetMapping("/filtrer/{nom}")
    @Operation(summary = "filtre une pizza par nom", description ="trouve une pizza dans la base de données par son nom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza trouvée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    PizzaResponseDto filtrerParNom(@PathVariable String nom){
        log.info("Pizza trouvée : {}", pizzaService.filtrerParNom(nom));
        return pizzaService.filtrerParNom(nom);
    }

    @GetMapping("/filtrer/ingredient/{nom}")
    @Operation(summary = "filtre une pizza par Ingredient", description ="trouve une pizza dans la base de données par son Ingrédient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza trouvée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    List<PizzaResponseDto> filtrerParIngredient(@PathVariable String nom){
        log.info("Pizza trouvée : {}", pizzaService.filtrerParIngredient(nom));
        return pizzaService.filtrerParIngredient(nom);

    }

}
