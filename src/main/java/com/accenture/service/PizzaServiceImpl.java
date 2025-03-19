package com.accenture.service;

import com.accenture.exception.PizzaException;
import com.accenture.repository.PizzaDAO;
import com.accenture.repository.entity.Pizza;
import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.service.dto.PizzaResponseDto;
import com.accenture.service.mapper.PizzaMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PizzaServiceImpl implements PizzaService {

    private final PizzaDAO pizzaDAO;
    private final PizzaMapper pizzaMapper;

    public PizzaServiceImpl(PizzaDAO pizzaDAO, PizzaMapper pizzaMapper) {
        this.pizzaDAO = pizzaDAO;
        this.pizzaMapper = pizzaMapper;
    }

    @Override
    public PizzaResponseDto ajouter(PizzaRequestDto pizzaRequestDto) throws PizzaException {
        verifierAjout(pizzaRequestDto);
        Pizza pizza = pizzaMapper.toPizza(pizzaRequestDto);
        Pizza pizzaEnreg = pizzaDAO.save(pizza);
        System.out.println(pizza);
        return pizzaMapper.toPizzaResponseDto(pizzaEnreg);
    }


    @Override
    public PizzaResponseDto supprimer(int id) throws EntityNotFoundException{
        Pizza pizzaASupprimer = pizzaDAO.findById(id).orElseThrow(()->new EntityNotFoundException("L'id n'existe pas"));
        pizzaASupprimer.setId(id);
        pizzaASupprimer.setActif(false);
        pizzaDAO.save(pizzaASupprimer);
        return pizzaMapper.toPizzaResponseDto(pizzaASupprimer);
    }

    private static void verifierAjout(PizzaRequestDto pizzaRequestDto) {
        if (pizzaRequestDto == null)
            throw new PizzaException("La pizzaRequestDto ne peux pas être nul");
        if (pizzaRequestDto.nom() == null || pizzaRequestDto.nom().isBlank())
            throw new PizzaException("Le nom ne peux pas être nul, ou vide");
        if (pizzaRequestDto.id_ingredient() == null || pizzaRequestDto.id_ingredient().isEmpty())
            throw new PizzaException("La liste des ingrédients ne peux pas être nul ou vide");
        if (pizzaRequestDto.tarif() == null)
            throw new PizzaException("Le tarif ne peux pas être nul");
        if (pizzaRequestDto.actif() == null)
            throw new PizzaException("Le status ne peux pas être nul");
    }



}
