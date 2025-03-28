package com.accenture.service.dto;

import com.accenture.shared.Taille;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;
@Schema(description = "Détails de la demande de pizza")
public record PizzaRequestDto (
        @Schema(description = "nom de la pizza", example = "Hawaïenne")
        @NotBlank(message = "Le nom est obligatoire")
        String nom,
         @Schema(description = "tarif de la pizza", example = "{'PETITE': 7.0,'MOYENNE': 10.0,'GRANDE': 17.5}")
         @NotNull(message =  "Le tarif est obligatoire")
         Map<Taille, Double> tarif,
         @Schema(description = "liste Ingrédient de la pizza", example = "[1,2]")
         @NotBlank(message = "La liste est obligatoire")
         List<Integer> id_ingredient,
         @Schema(description = "pizza disponible", example = "true")
         @NotBlank(message = "Le status est obligatoire")
         Boolean actif
) {
}
