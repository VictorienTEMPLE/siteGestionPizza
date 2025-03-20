package com.accenture.service.mapper;

import com.accenture.repository.entity.Pizza;
import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.service.dto.PizzaResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

public interface PizzaMapper {
    Pizza toPizza(PizzaRequestDto pizzaRequestDto);
    PizzaResponseDto toPizzaResponseDto(Pizza pizza);
}
