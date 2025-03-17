package com.accenture.service.dto;

import com.accenture.repository.entity.Ingredient;
import com.accenture.shared.Taille;

import java.util.List;

public record PizzaRequestDto (
         int id,
         String nom,
         Taille taille,
         List<Ingredient>ingredient,
         Double tarif,
         Boolean actif
){

}
