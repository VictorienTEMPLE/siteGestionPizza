package com.accenture.service.mapper;

import com.accenture.repository.IngredientDAO;
import com.accenture.repository.entity.Pizza;
import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.service.dto.PizzaResponseDto;
import com.accenture.shared.Taille;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PizzaMapperImpl implements PizzaMapper {

    private final IngredientDAO ingredientDAO;

    public PizzaMapperImpl(IngredientDAO ingredientDAO) {
        this.ingredientDAO = ingredientDAO;
    }

    @Override
    public Pizza toPizza(PizzaRequestDto pizzaRequestDto) {
        if (pizzaRequestDto == null) {
            return null;
        }

        Pizza pizza = new Pizza();
        pizza.setNom(pizzaRequestDto.nom());
        pizza.setTarif(pizzaRequestDto.tarif());
        pizza.setActif(pizzaRequestDto.actif());
        pizza.setIngredient(ingredientDAO.findAllById(pizzaRequestDto.id_ingredient()));
        return pizza;
    }

    @Override
    public PizzaResponseDto toPizzaResponseDto(Pizza pizza) {
        if ( pizza == null ) {
            return null;
        }

        int id = 0;
        String nom = null;
        Map<Taille, Double> tarif = null;
        List<String> nomIngredients = null;
        Boolean actif = null;

        id = pizza.getId();
        nom = pizza.getNom();
        tarif = pizza.getTarif();
        nomIngredients = pizza.getIngredient().stream().map(ingredient -> ingredient.getNom()).toList();

        actif = pizza.getActif();


        PizzaResponseDto pizzaResponseDto = new PizzaResponseDto( id, nom, tarif, nomIngredients, actif );

        return pizzaResponseDto;
    }
}
