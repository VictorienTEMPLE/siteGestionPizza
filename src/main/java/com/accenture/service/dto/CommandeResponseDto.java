package com.accenture.service.dto;

import com.accenture.shared.Statut;

import java.util.List;

public record CommandeResponseDto(
        int id,
        String nom_client,
        List<PizzaTailleQteResponseDto> listePizza,
        Statut statut,
        Double tarif
) {
}
