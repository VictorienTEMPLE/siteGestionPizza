package com.accenture.service;

import com.accenture.service.dto.ClientResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

import com.accenture.exception.ClientException;
import com.accenture.service.dto.ClientRequestDto;

public interface ClientService {
    List<ClientResponseDto> trouverTous();

    ClientResponseDto filtrerParId(int id) throws EntityNotFoundException;
    ClientResponseDto ajouter(ClientRequestDto clientRequestDto) throws ClientException;
}
