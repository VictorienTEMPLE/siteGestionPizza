package com.accenture.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Détails de la demande de client")
public record ClientRequestDto(

        @Schema(description = "Nom du client", example = "Lepetit")
        @NotBlank(message = "Le nom est obligatoire")
        @Size(min = 3, max = 20, message = "Le nom doit être entre 3 et 20 caractères")
        String nom,

        @Schema(description = "Mail du client", example = "JMClient@gmail.com")
        @NotNull(message = "Le mail est obligatoire")
        @Email(message = "Le format du mail est invalide")
        String mail,

        @Schema(description = "Statut vip du client", example = "true")
        @NotBlank(message = "Le statut vip est obligatoire")
        Boolean vip
) {
}
