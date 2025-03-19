package com.accenture.service;

import com.accenture.exception.PizzaException;
import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.service.dto.PizzaResponseDto;
import jakarta.persistence.EntityNotFoundException;

public interface PizzaService {
    public PizzaResponseDto ajouter(PizzaRequestDto pizzaRequestDto) throws PizzaException;

    PizzaResponseDto supprimer(int id) throws EntityNotFoundException;
}
