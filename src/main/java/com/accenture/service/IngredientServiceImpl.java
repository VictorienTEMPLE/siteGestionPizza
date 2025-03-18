package com.accenture.service;

import com.accenture.exception.IngredientException;
import com.accenture.repository.IngredientDAO;
import com.accenture.repository.entity.Ingredient;
import com.accenture.service.dto.IngredientRequestDto;
import com.accenture.service.dto.IngredientResponseDto;
import com.accenture.service.mapper.IngredientMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService{

    private final IngredientDAO ingredientDAO;
    private final IngredientMapper ingredientMapper;

    public IngredientServiceImpl(IngredientDAO ingredientDAO, IngredientMapper ingredientMapper) {
        this.ingredientDAO = ingredientDAO;
        this.ingredientMapper = ingredientMapper;
    }
    @Override
    public IngredientResponseDto ajouter(IngredientRequestDto ingredientRequestDto) throws IngredientException {
        verifierAjout(ingredientRequestDto);
        Ingredient ingredient=ingredientMapper.toIngredient(ingredientRequestDto);
        Ingredient ingredientEnreg = ingredientDAO.save(ingredient);
        return ingredientMapper.toIngredientResponseDto(ingredientEnreg);
    }

    private static void verifierAjout(IngredientRequestDto ingredientRequestDto) {
        if (ingredientRequestDto == null)
            throw new IngredientException("L'ingrédient ne peux pas être nul");
        if (ingredientRequestDto.nom()==null || ingredientRequestDto.nom().isBlank())
            throw new IngredientException("Le nom ne peux pas être null, ou vide");
        if (ingredientRequestDto.quantiteEnStock() == null)
            throw new IngredientException("La quantité ne peux pas être nul");
        if (ingredientRequestDto.enStock()==null)
            throw new IngredientException("Le status ne peux pas être nul");
    }
    @Override
    public List<IngredientResponseDto> lister() {
        List<Ingredient> listIngredient =ingredientDAO.findAll();
        return listIngredient.stream()
                .map(ingredientMapper::toIngredientResponseDto)
                .toList();
    }
}
