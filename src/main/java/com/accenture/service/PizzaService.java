package com.accenture.service;

import com.accenture.exception.PizzaException;
import com.accenture.service.dto.PizzaRequestDto;

public interface PizzaService {
    public PizzaException ajouter(PizzaRequestDto pizzaRequestDto) throws PizzaException;
    }
