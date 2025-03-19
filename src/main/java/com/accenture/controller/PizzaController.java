package com.accenture.controller;

import com.accenture.service.PizzaService;
import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.service.dto.PizzaResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @PostMapping
    ResponseEntity<PizzaResponseDto> ajouter(@RequestBody PizzaRequestDto pizzaRequestDto) {
        PizzaResponseDto pizzaResponseDtoAjoute = pizzaService.ajouter(pizzaRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(pizzaResponseDtoAjoute.id())
                .toUri();
        return ResponseEntity.created(location).body(pizzaResponseDtoAjoute);

    }

    @DeleteMapping("/{id}")
    ResponseEntity<PizzaResponseDto> supprimer(@PathVariable int id){
        PizzaResponseDto responseDto = pizzaService.supprimer(id);
        return ResponseEntity.ok(responseDto);
    }

}
