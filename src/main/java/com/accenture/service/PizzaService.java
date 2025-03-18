package com.accenture.service;

import com.accenture.exception.PizzaException;
import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.service.dto.PizzaResponseDto;

public interface PizzaService {
    public PizzaResponseDto ajouter(PizzaRequestDto pizzaRequestDto) throws PizzaException;
    }
