package com.accenture.service;

import com.accenture.exception.PizzaException;
import com.accenture.service.dto.PizzaRequestDto;
import org.springframework.stereotype.Service;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Override
    public PizzaException ajouter(PizzaRequestDto pizzaRequestDto) throws PizzaException {
            if (pizzaRequestDto == null)
                throw new PizzaException("La pizza ne peux pas être nulle");
            return null;
    }
}
