package com.accenture.service.dto;

import com.accenture.shared.Taille;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(description = "Détails de la demande de pizza-taille-quantite")
public record PizzaTailleQteRequestDto(
        @Schema(description = "id de pizza", example = "1")
        int id_pizza,

        @Schema(description = "taille de pizza", example = "GRANDE")
        @NotNull(message = "La taille est obligatoire")
        Taille taille,

        @Schema(description = "quantité de pizza", example = "1")
        @NotNull(message = "La quantité est obligatoire")
        Integer quantite
) {


}
