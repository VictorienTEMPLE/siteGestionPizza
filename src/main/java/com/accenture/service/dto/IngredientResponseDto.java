package com.accenture.service.dto;

public record IngredientResponseDto(
        int id,
        String nom,
        Integer quantiteEnStock,
        Boolean enStock
) {
}
