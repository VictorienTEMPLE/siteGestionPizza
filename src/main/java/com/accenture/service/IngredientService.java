package com.accenture.service;

import com.accenture.exception.IngredientException;
import com.accenture.service.dto.IngredientRequestDto;
import com.accenture.service.dto.IngredientResponseDto;

public interface IngredientService {
    IngredientResponseDto ajouter(IngredientRequestDto ingredientRequestDto) throws IngredientException;
}
