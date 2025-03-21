package com.accenture.service.dto;

import com.accenture.shared.Taille;

public record PizzaTailleQteResponseDto(
        int id,
        String nom_pizza,
        Taille taille,
        Integer quantite
) {
}
