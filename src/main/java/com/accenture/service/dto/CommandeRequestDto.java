package com.accenture.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "Détails de la demande de commande")
public record CommandeRequestDto(
        @Schema(description = "id de client", example = "1")
        int id_client,
        @Schema(description = "liste de pizza", example = "1,GRANDE,1")
        @NotNull(message = "La liste de pizza est obligatoire")
        List<PizzaTailleQteRequestDto> listePizza
) {
}
