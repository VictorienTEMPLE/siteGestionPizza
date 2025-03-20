package com.accenture.service;

import com.accenture.service.dto.ClientResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface ClientService {
    List<ClientResponseDto> trouverTous();

    ClientResponseDto filtrerParId(int id) throws EntityNotFoundException;
}
