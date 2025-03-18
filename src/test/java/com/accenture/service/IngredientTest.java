package com.accenture.service;

import com.accenture.exception.IngredientException;
import com.accenture.repository.IngredientDAO;
import com.accenture.repository.entity.Ingredient;
import com.accenture.service.dto.IngredientRequestDto;
import com.accenture.service.dto.IngredientResponseDto;
import com.accenture.service.mapper.IngredientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class IngredientTest {
    @Mock
    IngredientDAO ingredientDAO;

    @Mock
    IngredientMapper mapperMock;

    @InjectMocks
    IngredientServiceImpl service;


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
    void testAjouterQuantiteNegative(){
        IngredientException ie = Assertions.assertThrows(IngredientException.class, ()->service.ajouter(new IngredientRequestDto("Tomate",-5,true)));
        Assertions.assertEquals("La quantité ne peux pas être négative", ie.getMessage());
    }

    @Test
    void testAjouterEnstockNull(){
        IngredientException ie = Assertions.assertThrows(IngredientException.class, ()->service.ajouter(new IngredientRequestDto("Tomate",12,null)));
        Assertions.assertEquals("Le status ne peux pas être nul", ie.getMessage());
    }

    @Test
    void testAjouterQuantiteZeroEnStockTrue(){
        IngredientException ie = Assertions.assertThrows(IngredientException.class, ()->service.ajouter(new IngredientRequestDto("Tomate",0,true)));
        Assertions.assertEquals("Le enStock doit être false lors que la quantité est 0", ie.getMessage());
    }

    @Test
    void testAjouterOk(){
        IngredientRequestDto tomates = creerTomateRequestDto();
        IngredientResponseDto tomatesResponseDto = new IngredientResponseDto(1,"Tomates",12,true);
        Ingredient ingredientAvant = creerTomate();
        Ingredient ingredientApres = creerTomate();
        Mockito.when(mapperMock.toIngredient(tomates)).thenReturn(ingredientAvant);
        Mockito.when(ingredientDAO.save(ingredientAvant)).thenReturn(ingredientApres);
        Mockito.when(mapperMock.toIngredientResponseDto(ingredientApres)).thenReturn(tomatesResponseDto);
        Assertions.assertSame(tomatesResponseDto, service.ajouter(tomates));
        Mockito.verify(ingredientDAO, Mockito.times(1)).save(ingredientAvant);
    }




    @Test
    void testModifierNull(){
        Mockito.when(ingredientDAO.findById(1)).thenReturn(Optional.of(creerTomate()));
        IngredientException ie = Assertions.assertThrows(IngredientException.class, ()->service.modifier(1, null));
        Assertions.assertEquals("L'ingrédient ne peux pas être nul",ie.getMessage());
    }

    @Test
    void testModifierIdExistePas(){
        Mockito.when(ingredientDAO.findById(1)).thenReturn(Optional.empty());
        EntityNotFoundException ie = Assertions.assertThrows(EntityNotFoundException.class, ()->service.modifier(1, new IngredientRequestDto("   ",12,true)));
        Assertions.assertEquals("L'id n'existe pas",ie.getMessage());

    }

    @Test
    void testModifierNomBlank(){
        Ingredient nouvelleTomate = new Ingredient("   ",12,true);
        IngredientRequestDto tomateRequestDto = new IngredientRequestDto("   ",12,true);
        Mockito.when(ingredientDAO.findById(1)).thenReturn(Optional.of(creerTomate()));
        Mockito.when(mapperMock.toIngredient(tomateRequestDto)).thenReturn(nouvelleTomate);
        IngredientException ie = Assertions.assertThrows(IngredientException.class, ()->service.modifier(1,tomateRequestDto));
        Assertions.assertEquals("Le nom ne peux pas être vide",ie.getMessage());
    }

    @Test
    void testModifierQuantiteNegative(){
        Ingredient nouvelleTomate = new Ingredient("Tomate", -5,true);
        IngredientRequestDto tomateRequestDto = new IngredientRequestDto("Tomate",-5,true);
        Mockito.when(ingredientDAO.findById(1)).thenReturn(Optional.of(creerTomate()));
        Mockito.when(mapperMock.toIngredient(tomateRequestDto)).thenReturn(nouvelleTomate);
        IngredientException ie = Assertions.assertThrows(IngredientException.class, ()->service.modifier(1,tomateRequestDto));
        Assertions.assertEquals("La quantité ne peux pas être négative", ie.getMessage());
    }

    @Test
    void testModifierQuantiteEnZeroEnStockEstEnTrue(){
        Ingredient nouvelleTomate = new Ingredient("Tomate", 0,true);
        IngredientRequestDto tomateRequestDto = new IngredientRequestDto("Tomate",0,true);
        Mockito.when(ingredientDAO.findById(1)).thenReturn(Optional.of(creerTomate()));
        Mockito.when(mapperMock.toIngredient(tomateRequestDto)).thenReturn(nouvelleTomate);
        IngredientException ie = Assertions.assertThrows(IngredientException.class, ()->service.modifier(1,tomateRequestDto));
        Assertions.assertEquals("Le enStock doit être false lors que la quantité est 0", ie.getMessage());
    }


    @Test
    void testModifierOK(){
        Ingredient tomateAModifier = creerTomate();
        IngredientRequestDto tomateRequestDto = new IngredientRequestDto("Tomate", 10, true);
        Ingredient tomateEnreg = new Ingredient("Tomate", 10, true);
        tomateEnreg.setId(1);
        IngredientResponseDto tomateResponseDto = creerTomateResponseDto();
        Mockito.when(ingredientDAO.findById(1)).thenReturn(Optional.of(tomateAModifier));
        Mockito.when(mapperMock.toIngredient(tomateRequestDto)).thenReturn(tomateEnreg);
        Mockito.when(mapperMock.toIngredientResponseDto(tomateEnreg)).thenReturn(tomateResponseDto);
        Mockito.when(ingredientDAO.save(tomateAModifier)).thenReturn(tomateEnreg);
        assertEquals(tomateResponseDto,service.modifier(1, tomateRequestDto));
        Mockito.verify(ingredientDAO).save(tomateAModifier);


    }

    private static IngredientResponseDto creerTomateResponseDto() {
        return new IngredientResponseDto(1, "Tomate", 10, true);
    }


    private static IngredientRequestDto creerTomateRequestDto() {
        return new IngredientRequestDto("Tomate", 12, true);
    }

    private static Ingredient creerTomate() {
        return new Ingredient("Tomate", 12, true);
    }






}
