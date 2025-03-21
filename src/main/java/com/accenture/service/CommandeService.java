package com.accenture.service;

import com.accenture.exception.CommandeException;
import com.accenture.service.dto.CommandeRequestDto;
import com.accenture.service.dto.CommandeResponseDto;

public interface CommandeService {
    CommandeResponseDto ajouter(CommandeRequestDto commandeRequestDto) throws CommandeException;
}
