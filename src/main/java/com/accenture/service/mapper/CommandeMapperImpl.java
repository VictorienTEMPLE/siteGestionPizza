package com.accenture.service.mapper;

import com.accenture.exception.CommandeException;
import com.accenture.repository.ClientDAO;
import com.accenture.repository.CommandeDAO;
import com.accenture.repository.PizzaDAO;
import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Commande;
import com.accenture.repository.entity.PizzaTailleQuantite;
import com.accenture.service.dto.CommandeRequestDto;
import com.accenture.service.dto.CommandeResponseDto;
import com.accenture.service.dto.PizzaTailleQteResponseDto;
import com.accenture.shared.Statut;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandeMapperImpl implements CommandeMapper{
    private final CommandeDAO commandeDAO;
    private final ClientDAO clientDAO;
    private final PizzaTailleQteMapper pizzaTailleQteMapper;
    private final PizzaDAO pizzaDAO;


    public CommandeMapperImpl(CommandeDAO commandeDAO, ClientDAO clientDAO, PizzaTailleQteMapper pizzaTailleQteMapper, PizzaDAO pizzaDAO) {
        this.commandeDAO = commandeDAO;
        this.clientDAO = clientDAO;
        this.pizzaTailleQteMapper = pizzaTailleQteMapper;
        this.pizzaDAO = pizzaDAO;
    }


    @Override
    public Commande toCommande(CommandeRequestDto commandeRequestDto) throws CommandeException{
        if (commandeRequestDto == null) {
            return null;
        }

        Client client = clientDAO.findById(commandeRequestDto.id_client()).orElseThrow(()->new CommandeException("Le client n'existe pas"));
        List<PizzaTailleQuantite> listePizzaTailleQte = commandeRequestDto.listePizza()
                .stream()
                .map(pizzaTailleQteMapper::toPizzaTailleQuantite)
                .toList();
        double tarif = 0.0;
        for(PizzaTailleQuantite p : listePizzaTailleQte){
            tarif += p.getPizza().getTarif().get(p.getTaille())*p.getQuantite();
        }
        Commande commande = new Commande();
        commande.setClient(client);
        commande.setListePizza(listePizzaTailleQte);
        commande.setStatut(Statut.EN_ATTENTE);
        commande.setTarif(tarif);
        return commande;
    }

    @Override
    public CommandeResponseDto toCommandeResponseDto(Commande commande) {

        if (commande == null) {
            return null;
        }

        String nom_client = commande.getClient().getNom();

        List<PizzaTailleQteResponseDto> listePizzaTailleQteResDto = commande.getListePizza().stream()
                .map(pizzaTailleQteMapper::toPizzaTailleQteResponseDto)
                .toList();

       return new CommandeResponseDto(commande.getId(), nom_client,listePizzaTailleQteResDto, commande.getStatut(),commande.getTarif());

    }
}
