package com.accenture.service;

import com.accenture.exception.PizzaException;
import com.accenture.exception.PizzaException;
import com.accenture.repository.PizzaDAO;
import com.accenture.repository.entity.Ingredient;
import com.accenture.repository.entity.Pizza;
import com.accenture.service.dto.IngredientRequestDto;
import com.accenture.service.dto.IngredientResponseDto;
import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.service.dto.PizzaResponseDto;
import com.accenture.service.mapper.PizzaMapper;
import com.accenture.shared.Taille;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PizzaTest {
    @Mock //on crée le mockito.mock ici
    PizzaDAO daoMock = Mockito.mock(PizzaDAO.class);
    @Mock
    PizzaMapper mapperMock;
    @InjectMocks //on créer le new tachServiceImpl ici
    PizzaServiceImpl service;

    private List<Ingredient> listIngredients(){
        Ingredient ingredient1 = new Ingredient("Tomates",12,true);
        Ingredient ingredient2 = new Ingredient("olives",30,true);
        List<Ingredient> listeIngredient= List.of(ingredient1,ingredient2);
        return listeIngredient;
    }

    private List<Ingredient> listeIngredientVide(){

        return Collections.emptyList();
    }

    private HashMap<Taille, Double> tarifDefini(){
        HashMap<Taille, Double> map = new HashMap<>();
        map.put(Taille.PETITE, 7.0);
        map.put(Taille.MOYENNE,12.0);
        map.put(Taille.GRANDE,17.5);
        return map;
    }

    @Test
    void testAjouterNull() {
        PizzaException ie = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(null));
        Assertions.assertEquals("La pizzaRequestDto ne peux pas être nul", ie.getMessage());
    }


    @Test
    void testAjouterNomNull() {
        PizzaException ie = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(new PizzaRequestDto( null, tarifDefini(), List.of(1,2),true)));
        Assertions.assertEquals("Le nom ne peux pas être nul, ou vide", ie.getMessage());
    }

    @Test
    void testAjouterNomBlank() {
        PizzaException ie = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(new PizzaRequestDto( "", tarifDefini(), List.of(1,2),true)));
        Assertions.assertEquals("Le nom ne peux pas être nul, ou vide", ie.getMessage());
    }


    @Test
    void testAjouterListeIngredientNull(){
        PizzaException ie = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(new PizzaRequestDto( "Quattre Fromages", tarifDefini(), null,true)));
        Assertions.assertEquals("La liste des ingrédients ne peux pas être nul ou vide", ie.getMessage());
    }

    @Test
    void testAjouterListeIngredientvide(){
        PizzaException ie = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(new PizzaRequestDto("Quattre Fromages", tarifDefini(), List.of(),true)));
        Assertions.assertEquals("La liste des ingrédients ne peux pas être nul ou vide", ie.getMessage());
    }

    @Test
    void testAjoutertarifNull(){
        PizzaException ie = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(new PizzaRequestDto( "Quattre Fromages", null, List.of(1,2),true)));
        Assertions.assertEquals("Le tarif ne peux pas être nul", ie.getMessage());
    }

    @Test
    void testAjouteractifNull(){
        PizzaException ie = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(new PizzaRequestDto( "Quattre Fromages", tarifDefini(), List.of(1,2),null)));
        Assertions.assertEquals("Le status ne peux pas être nul", ie.getMessage());
    }

    @Test
    void TestAjouterOk(){
        PizzaRequestDto pizzaRequestDto = new PizzaRequestDto("Quattre Fromages", tarifDefini(), List.of(1,2),true);
        PizzaResponseDto pizzaResponseDto = new PizzaResponseDto(1,"Quattre Fromages", tarifDefini(), List.of("tomates","fromages"),true);
        Pizza pizzaAvant = new Pizza("Quattre Fromages",tarifDefini() , listIngredients(),true);
        Pizza pizzaApres = new Pizza("Quattre Fromages",tarifDefini() , listIngredients(),true);
        Mockito.when(mapperMock.toPizza(pizzaRequestDto)).thenReturn(pizzaAvant);
        Mockito.when(daoMock.save(pizzaAvant)).thenReturn(pizzaApres);
        Mockito.when(mapperMock.toPizzaResponseDto(pizzaApres)).thenReturn(pizzaResponseDto);
        Assertions.assertSame(pizzaResponseDto, service.ajouter(pizzaRequestDto));
        Mockito.verify(daoMock, Mockito.times(1)).save(pizzaAvant);
    }

}

