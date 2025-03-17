package com.accenture.controller;

import com.accenture.service.IngredientService;
import com.accenture.service.dto.IngredientRequestDto;
import com.accenture.service.dto.IngredientResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
}
