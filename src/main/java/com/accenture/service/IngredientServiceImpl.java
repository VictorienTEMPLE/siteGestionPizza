package com.accenture.service;

import com.accenture.exception.IngredientException;
import com.accenture.repository.IngredientDAO;
import com.accenture.repository.entity.Ingredient;
import com.accenture.service.dto.IngredientRequestDto;
import com.accenture.service.dto.IngredientResponseDto;
import com.accenture.service.mapper.IngredientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;


@Service
public class IngredientServiceImpl implements IngredientService{

    public static final String ID_EXISTE_PAS = "L'id n'existe pas";
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

    @Override
    public IngredientResponseDto modifier(int id, IngredientRequestDto ingredientRequestDto) throws IngredientException, EntityNotFoundException{
        Optional<Ingredient> optIngredient = ingredientDAO.findById(id);
        if (optIngredient.isEmpty())
            throw new EntityNotFoundException(ID_EXISTE_PAS);
        Ingredient ingredientExistante = optIngredient.get();
        Ingredient nouvelle = ingredientMapper.toIngredient(ingredientRequestDto);
        verifierEtRemplacer(nouvelle, ingredientExistante);
        Ingredient ingredientEnreg = ingredientDAO.save(ingredientExistante);
        return ingredientMapper.toIngredientResponseDto(ingredientEnreg);
    }
  
  
    @Override
    public List<IngredientResponseDto> lister() {
        List<Ingredient> listIngredient =ingredientDAO.findAll();
        return listIngredient.stream()
                .map(ingredientMapper::toIngredientResponseDto)
                .toList();
    }
    private static void verifierAjout(IngredientRequestDto ingredientRequestDto) {
        if (ingredientRequestDto == null)
            throw new IngredientException("L'ingrédient ne peux pas être nul");
        if (ingredientRequestDto.nom()==null || ingredientRequestDto.nom().isBlank())
            throw new IngredientException("Le nom ne peux pas être null, ou vide");
        if (ingredientRequestDto.quantiteEnStock() == null)
            throw new IngredientException("La quantité ne peux pas être nul");
        if(ingredientRequestDto.quantiteEnStock() < 0)
            throw new IngredientException("La quantité ne peux pas être négative");
        if (ingredientRequestDto.enStock() == null)
            throw new IngredientException("Le status ne peux pas être nul");
        if(ingredientRequestDto.quantiteEnStock() == 0 && Boolean.TRUE.equals(ingredientRequestDto.enStock()))
            throw new IngredientException("Le enStock doit être false lors que la quantité est 0");
    }
  
      
       private void verifierEtRemplacer(Ingredient nouvelle, Ingredient ingredientExistante) {
        if(nouvelle == null)
            throw new IngredientException("L'ingrédient ne peux pas être nul");
        if(nouvelle.getNom() != null){
            if(nouvelle.getNom().isBlank())
                throw new IngredientException("Le nom ne peux pas être vide");
            else ingredientExistante.setNom(nouvelle.getNom());}
        if(nouvelle.getQuantiteEnStock() != null) {
            if (nouvelle.getQuantiteEnStock() < 0)
                throw new IngredientException("La quantité ne peux pas être négative");
            else ingredientExistante.setQuantiteEnStock(nouvelle.getQuantiteEnStock());
        }
        if(nouvelle.getEnStock() != null)
            ingredientExistante.setEnStock(nouvelle.getEnStock());
        if(nouvelle.getQuantiteEnStock() == 0 && Boolean.TRUE.equals(nouvelle.getEnStock()))
            throw new IngredientException("Le enStock doit être false lors que la quantité est 0");
    }

    
}
