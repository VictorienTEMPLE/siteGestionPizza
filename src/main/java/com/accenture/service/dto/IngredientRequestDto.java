package com.accenture.service.dto;

public record IngredientRequestDto(
        String nom,
        Integer quantiteEnStock,
        Boolean enStock
) {

}
