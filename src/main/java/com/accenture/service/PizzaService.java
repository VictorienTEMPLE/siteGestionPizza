package com.accenture.service;

import com.accenture.exception.PizzaException;
import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.service.dto.PizzaResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface PizzaService {
    public PizzaResponseDto ajouter(PizzaRequestDto pizzaRequestDto) throws PizzaException;

    PizzaResponseDto supprimer(int id) throws EntityNotFoundException;

    List<PizzaResponseDto> trouverTous();

    PizzaResponseDto filtrerParId(int id) throws EntityNotFoundException;

    PizzaResponseDto filtrerParNom(String nom) throws EntityNotFoundException;

    List<PizzaResponseDto> filtrerParIngredient(String nom);

    PizzaResponseDto modifier(int id, PizzaRequestDto pizzaRequestDto) throws  PizzaException, EntityNotFoundException;
}
