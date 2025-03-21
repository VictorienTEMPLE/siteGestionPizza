package com.accenture.service;

import com.accenture.repository.ClientDAO;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import com.accenture.exception.ClientException;
import com.accenture.service.dto.ClientRequestDto;
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

    /**
     * <p>La méthode <code>trouverTous</code> permet de récupérer tous les clients présents dans la base de données.</p>
     *
     * @return Une liste d'objets <code>ClientResponseDto</code> représentant tous les clients.
     */
    @Override
    public List<ClientResponseDto> trouverTous(){
        return clientDAO.findAll()
                .stream()
                .map(clientMapper::toClientResponseDto)
                .toList();
    }
    /**
     * <p>La méthode <code>filtrerParId</code> permet de récupérer un client spécifique par son id.</p>
     *
     * @param id L'id du client à rechercher.
     * @return Un objet <code>ClientResponseDto</code> représentant le client trouvé.
     * @throws EntityNotFoundException Si le client avec l'id donné n'est pas trouvé dans la base de données.
     */
    @Override
    public ClientResponseDto filtrerParId(int id) throws EntityNotFoundException {
        Client clientTrouve = clientDAO.findById(id).orElseThrow(()-> new EntityNotFoundException(ID_EXISTE_PAS));
        return clientMapper.toClientResponseDto(clientTrouve);
    }

    /**
     * <p>La méthode <code>ajouter</code> permet d'ajouter un nouveau client dans la base de données.</p>
     *
     * @param clientRequestDto Les informations du client à ajouter.
     * @return Un objet <code>ClientResponseDto</code> représentant le client ajouté.
     * @throws ClientException Si une erreur se produit lors de la validation du client.
     */
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
