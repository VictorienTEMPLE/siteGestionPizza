package com.accenture.service;

import com.accenture.repository.ClientDAO;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{
    public static final String ID_EXISTE_PAS = "L'id n'existe pas";
    private final ClientDAO clientDAO;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientDAO clientDAO, ClientMapper clientMapper) {
        this.clientDAO = clientDAO;
        this.clientMapper = clientMapper;
    }

    @Override
    public List<ClientResponseDto> trouverTous(){
        return clientDAO.findAll()
                .stream()
                .map(clientMapper::toClientResponseDto)
                .toList();
    }

    @Override
    public ClientResponseDto filtrerParId(int id) throws EntityNotFoundException {
        Client clientTrouve = clientDAO.findById(id).orElseThrow(()-> new EntityNotFoundException(ID_EXISTE_PAS));
        return clientMapper.toClientResponseDto(clientTrouve);
    }
}
