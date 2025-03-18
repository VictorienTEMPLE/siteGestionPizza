package com.accenture.controller;

import com.accenture.service.IngredientService;
import com.accenture.service.dto.IngredientRequestDto;
import com.accenture.service.dto.IngredientResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    ResponseEntity<IngredientResponseDto> ajouter(@RequestBody IngredientRequestDto ingredientRequestDto){
        IngredientResponseDto ingredientResponseDtoAjouter = ingredientService.ajouter(ingredientRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(ingredientResponseDtoAjouter.id())
                .toUri();
        return ResponseEntity.created(location).body(ingredientResponseDtoAjouter);
    }

    @GetMapping
    List<IngredientResponseDto> trouverTous(){
        return ingredientService.lister();
    }

    @PatchMapping("/{id}")
    ResponseEntity<IngredientResponseDto> modifier(@PathVariable int id, @RequestBody IngredientRequestDto ingredientRequestDto){
        IngredientResponseDto responseDto = ingredientService.modifier(id,ingredientRequestDto);
        return ResponseEntity.ok(responseDto);
    }

}
