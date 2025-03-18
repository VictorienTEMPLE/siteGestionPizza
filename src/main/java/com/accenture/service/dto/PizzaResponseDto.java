package com.accenture.service.dto;

import com.accenture.repository.entity.Ingredient;
import com.accenture.shared.Taille;

import java.util.HashMap;
import java.util.List;

public record PizzaResponseDto(
        String nom,
        HashMap<Taille, Double> tarif,
        List<Ingredient> ingredient,
        Boolean actif) {
}
