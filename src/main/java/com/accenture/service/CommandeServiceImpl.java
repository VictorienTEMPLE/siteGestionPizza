package com.accenture.service;

import com.accenture.exception.CommandeException;
import com.accenture.repository.CommandeDAO;
import com.accenture.repository.PizzaDAO;
import com.accenture.repository.entity.Commande;
import com.accenture.service.dto.CommandeRequestDto;
import com.accenture.service.dto.CommandeResponseDto;
import com.accenture.service.dto.PizzaTailleQteRequestDto;
import com.accenture.service.mapper.CommandeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeServiceImpl implements CommandeService{
    private final CommandeMapper commandeMapper;
    private final CommandeDAO commandeDAO;
    private final PizzaDAO pizzaDAO;


    public CommandeServiceImpl(CommandeMapper commandeMapper, CommandeDAO commandeDAO, PizzaDAO pizzaDAO) {
        this.commandeMapper = commandeMapper;
        this.commandeDAO = commandeDAO;
        this.pizzaDAO = pizzaDAO;
    }

    @Override
    public CommandeResponseDto ajouter(CommandeRequestDto commandeRequestDto) throws CommandeException{
        System.out.println("commandeRequestDto est " + commandeRequestDto);
        verifierCommande(commandeRequestDto);
        List<PizzaTailleQteRequestDto> listePizzaNonDispo = commandeRequestDto.listePizza().stream()
                .filter(pizzaTailleQteRequestDto -> !pizzaDAO.findById(pizzaTailleQteRequestDto.id_pizza()).get().getActif())
                .toList();
        System.out.println("listePizzaNonDispo est " + listePizzaNonDispo);
        if(!listePizzaNonDispo.isEmpty())
            throw new CommandeException("Impossible de commander un pizza non disponible");
        Commande commandeAvantEnreg = commandeMapper.toCommande(commandeRequestDto);
        Commande commandeEnreg = commandeDAO.save(commandeAvantEnreg);
        return commandeMapper.toCommandeResponseDto(commandeEnreg);
    }

    private static void verifierCommande(CommandeRequestDto commandeRequestDto) {
        if(commandeRequestDto.id_client()<=0)
            throw new CommandeException("L'id de client ne peut pas être zéro ou négatif");
        if(commandeRequestDto.listePizza() == null || commandeRequestDto.listePizza().isEmpty())
            throw new CommandeException("La liste de pizza ne peut pas être null ou vide");
    }


}
