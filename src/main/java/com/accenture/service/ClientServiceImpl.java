package com.accenture.service;

import com.accenture.repository.ClientDAO;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDAO;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
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
    @Override
    public ClientResponseDto ajouter(ClientRequestDto clientRequestDto) throws ClientException{
        verifierClientDto(clientRequestDto);
        Client client = clientMapper.toClient(clientRequestDto);
        Client clientEnreg = clientDAO.save(client);
        return clientMapper.toClientResponseDto(clientEnreg);
    }

    private static void verifierClientDto(ClientRequestDto clientRequestDto) {
        if(clientRequestDto == null)
            throw new ClientException("Le client ne peut pas être nul");
        if(clientRequestDto.nom() == null || clientRequestDto.nom().isBlank())
            throw new ClientException("Le nom du client ne peut pas être nul ou vide");
        if(clientRequestDto.mail() == null || clientRequestDto.mail().isBlank())
            throw new ClientException("Le mail du client ne peut pas être nul ou vide");
        if (!clientRequestDto.mail().contains("@"))
            throw new ClientException("Le format de mail du client est invalide");
        if(clientRequestDto.vip() == null)
            throw new ClientException("Le statut vip du client ne peut pas être nul");
    }


}
