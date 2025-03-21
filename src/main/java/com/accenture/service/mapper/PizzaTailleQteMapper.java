package com.accenture.service.mapper;

import com.accenture.repository.entity.PizzaTailleQuantite;
import com.accenture.service.dto.PizzaTailleQteRequestDto;
import com.accenture.service.dto.PizzaTailleQteResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PizzaTailleQteMapper {
    PizzaTailleQuantite toPizzaTailleQuantite(PizzaTailleQteRequestDto pizzaTailleQteRequestDto);
    PizzaTailleQteResponseDto toPizzaTailleQteResponseDto(PizzaTailleQuantite pizzaTailleQuantite);
}
