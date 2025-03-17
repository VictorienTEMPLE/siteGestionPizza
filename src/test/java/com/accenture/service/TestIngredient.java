package com.accenture.service;

import com.accenture.exception.IngredientException;
import com.accenture.repository.IngredientDAO;
import com.accenture.repository.entity.Ingredient;
import com.accenture.service.dto.IngredientRequestDto;
import com.accenture.service.dto.IngredientRequestDto;
import com.accenture.service.dto.IngredientResponseDto;
import com.accenture.service.mapper.IngredientMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestIngredient {
    @Mock
    IngredientDAO ingredientDAO;

    @InjectMocks //on créer le new tachServiceImpl ici
    IngredientServiceImpl service;

    @Mock
    IngredientMapper mapperMock;



    @Test
    void testAjouterNull(){
       IngredientException ie = Assertions.assertThrows(IngredientException.class, ()->service.ajouter(null));
       Assertions.assertEquals("L'ingrédient ne peux pas être nul",ie.getMessage());
    }

    @Test
    void testAjouterNomNull(){
        IngredientException ie = Assertions.assertThrows(IngredientException.class, ()->service.ajouter(new IngredientRequestDto(null,12,true)));
        Assertions.assertEquals("Le nom ne peux pas être null, ou vide", ie.getMessage());
    }
    @Test
    void testAjouterNomBlank(){
        IngredientException ie = Assertions.assertThrows(IngredientException.class, ()->service.ajouter(new IngredientRequestDto("",12,true)));
        Assertions.assertEquals("Le nom ne peux pas être null, ou vide", ie.getMessage());
    }

    @Test
    void testAjouterQuantiteNull(){
        IngredientException ie = Assertions.assertThrows(IngredientException.class, ()->service.ajouter(new IngredientRequestDto("Tomate",null,true)));
        Assertions.assertEquals("La quantité ne peux pas être nul", ie.getMessage());
    }

    @Test
    void testAjouterEnstockNull(){
        IngredientException ie = Assertions.assertThrows(IngredientException.class, ()->service.ajouter(new IngredientRequestDto("Tomate",12,null)));
        Assertions.assertEquals("Le status ne peux pas être nul", ie.getMessage());
    }

    @Test
    void testAjouterOk(){
        IngredientRequestDto tomates = new IngredientRequestDto("Tomate",12,true);
        IngredientResponseDto tomatesResponseDto = new IngredientResponseDto(1,"Tomates",12,true);
        Ingredient ingredientAvant = new Ingredient("Tomate",12,true);
        Ingredient ingredientApres = new Ingredient("Tomate",12,true);

        Mockito.when(mapperMock.toIngredient(tomates)).thenReturn(ingredientAvant);
        Mockito.when(ingredientDAO.save(ingredientAvant)).thenReturn(ingredientApres);
        Mockito.when(mapperMock.toIngredientResponseDto(ingredientApres)).thenReturn(tomatesResponseDto);
        Assertions.assertSame(tomatesResponseDto, service.ajouter(tomates));
        Mockito.verify(ingredientDAO, Mockito.times(1)).save(ingredientAvant);
    }

}
