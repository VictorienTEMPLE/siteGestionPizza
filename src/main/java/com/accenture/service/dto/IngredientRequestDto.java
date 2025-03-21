package com.accenture.service.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Détails de la demande d'ingrédient")
public record IngredientRequestDto(
        @Schema(description = "Nom de l'ingrédient", example = "Tomate")
        @NotBlank(message = "Le nom est obligatoire")
        String nom,

        @Schema(description = "Quantité en stock de l'ingrédient", example = "12")
        @NotNull(message = "La quantité en stock est obligatoire")
        @PositiveOrZero(message = "La quantité en stock doit être un entier positif ou zéro")
        Integer quantiteEnStock,

        @Schema(description = "Statut en stock de l'ingrédient", example = "true")
        @NotBlank(message = "Le statut en stock est obligatoire")
        Boolean enStock
) {

}
