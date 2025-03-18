package com.accenture.service;

import com.accenture.exception.IngredientException;
import com.accenture.service.dto.IngredientRequestDto;
import com.accenture.service.dto.IngredientResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface IngredientService {
    IngredientResponseDto ajouter(IngredientRequestDto ingredientRequestDto) throws IngredientException;

    IngredientResponseDto modifier(int id, IngredientRequestDto ingredientRequestDto) throws IngredientException, EntityNotFoundException;

    List<IngredientResponseDto> lister();
}
