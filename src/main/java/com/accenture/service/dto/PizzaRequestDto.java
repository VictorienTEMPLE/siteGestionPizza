package com.accenture.service.dto;

import com.accenture.shared.Taille;

import java.util.List;
import java.util.Map;

public record PizzaRequestDto (
         String nom,
         Map<Taille, Double> tarif,
         List<Integer> id_ingredient,
         Boolean actif
) {

}
