package com.accenture.service.mapper;

import com.accenture.exception.PizzaException;
import com.accenture.repository.PizzaDAO;
import com.accenture.repository.entity.Pizza;
import com.accenture.repository.entity.PizzaTailleQuantite;
import com.accenture.service.dto.PizzaTailleQteRequestDto;
import com.accenture.service.dto.PizzaTailleQteResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PizzaTailleQteMapperImpl implements PizzaTailleQteMapper{
    private final PizzaDAO pizzaDAO;

    public PizzaTailleQteMapperImpl(PizzaDAO pizzaDAO) {
        this.pizzaDAO = pizzaDAO;
    }


    @Override
    public PizzaTailleQuantite toPizzaTailleQuantite(PizzaTailleQteRequestDto pizzaTailleQteRequestDto) {
        if (pizzaTailleQteRequestDto == null) {
            return null;
        }

        Pizza pizza = pizzaDAO.findById(pizzaTailleQteRequestDto.id_pizza()).orElseThrow(()->new PizzaException("Pizza n'existe pas"));

        PizzaTailleQuantite pizzaTailleQuantite = new PizzaTailleQuantite();
        pizzaTailleQuantite.setPizza(pizza);
        pizzaTailleQuantite.setTaille(pizzaTailleQteRequestDto.taille());
        pizzaTailleQuantite.setQuantite(pizzaTailleQteRequestDto.quantite());
        return pizzaTailleQuantite;

    }

    @Override
    public PizzaTailleQteResponseDto toPizzaTailleQteResponseDto(PizzaTailleQuantite pizzaTailleQuantite) {
        return new PizzaTailleQteResponseDto(
                pizzaTailleQuantite.getId(),
                pizzaTailleQuantite.getPizza().getNom(),
                pizzaTailleQuantite.getTaille(),
                pizzaTailleQuantite.getQuantite()
        );
    }
}
