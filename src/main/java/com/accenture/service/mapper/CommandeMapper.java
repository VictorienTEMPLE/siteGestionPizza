package com.accenture.service.mapper;

import com.accenture.repository.entity.Commande;
import com.accenture.service.dto.CommandeRequestDto;
import com.accenture.service.dto.CommandeResponseDto;
import org.mapstruct.Mapper;

public interface CommandeMapper {
    Commande toCommande(CommandeRequestDto commandeRequestDto);
    CommandeResponseDto toCommandeResponseDto(Commande commande);
}
