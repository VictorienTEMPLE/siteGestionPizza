package com.accenture.controller;

import com.accenture.repository.entity.Ingredient;
import com.accenture.service.IngredientService;
import com.accenture.service.dto.IngredientRequestDto;
import com.accenture.service.dto.IngredientResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ingredients")
@Tag(name="Ingrédients", description = "Gestion des ingrédients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
     * <p>La méthode <code>ajouter</code> permet d'ajouter un nouvel ingrédient à travers une requête HTTP POST.</p>
     *
     * @param ingredientRequestDto Les informations de l'ingrédient à ajouter, envoyées dans le corps de la requête.
     * @return Un objet <code>ResponseEntity</code> contenant un statut HTTP 201 (Created) et le corps de la réponse
     *         avec l'objet <code>IngredientResponseDto</code> représentant l'ingrédient créé.
     */
    @PostMapping
    @Operation(summary = "Ajouter un nouvel ingrédient", description ="Ajoute un nouvel ingrédient à la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingrédient créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<IngredientResponseDto> ajouter(@RequestBody IngredientRequestDto ingredientRequestDto){
        log.info("L'ajoute de l'ingrédient commence avec son nom : {}", ingredientRequestDto.nom());
        IngredientResponseDto ingredientResponseDtoAjouter = ingredientService.ajouter(ingredientRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(ingredientResponseDtoAjouter.id())
                .toUri();
        return ResponseEntity.created(location).body(ingredientResponseDtoAjouter);
    }


    /**
     * <p>La méthode <code>trouverTous</code> permet de récupérer la liste de tous les ingrédients via une requête HTTP GET.</p>
     *
     * @return Une liste d'objets <code>IngredientResponseDto</code> représentant tous les ingrédients.
     */
    @GetMapping
    @Operation(summary = "Récupérer tous les ingrédients", description ="Récupération de tous les ingrédients depuis la base de données")
    @ApiResponse(responseCode = "200", description = "Récupération effectuée avec succès")
    List<IngredientResponseDto> trouverTous(){
        log.info("La récupération de tous les ingrédients commence");
        return ingredientService.lister();
    }


    /**
     * <p>La méthode <code>modifier</code> permet de modifier un ingrédient existant par son id via une requête HTTP PATCH.</p>
     *
     * @param id L'id de l'ingrédient à modifier, passé en paramètre de l'URL.
     * @param ingredientRequestDto Les informations de l'ingrédient à modifier, envoyées dans le corps de la requête.
     * @return Un objet <code>ResponseEntity</code> contenant un statut HTTP 200 (OK) et le corps de la réponse
     *         avec l'objet <code>IngredientResponseDto</code> représentant l'ingrédient modifié.
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Modifier un ingrédient", description ="Modification un ingrédient dans la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modification effectuée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<IngredientResponseDto> modifier(@PathVariable int id, @RequestBody IngredientRequestDto ingredientRequestDto){
        log.info("La modification de l'ingrédient commence avec son id : {}", id);
        IngredientResponseDto responseDto = ingredientService.modifier(id,ingredientRequestDto);
        return ResponseEntity.ok(responseDto);
    }

}
