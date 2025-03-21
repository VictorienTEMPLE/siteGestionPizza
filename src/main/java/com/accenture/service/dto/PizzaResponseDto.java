package com.accenture.service.dto;

import com.accenture.shared.Taille;
import java.util.List;
import java.util.Map;

public record PizzaResponseDto(
        int id,
        String nom,
        Map<Taille, Double> tarif,
        List<String> nomIngredients,
        Boolean actif
) {
}
