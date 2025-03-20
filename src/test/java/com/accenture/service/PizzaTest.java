package com.accenture.service;

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
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PizzaTest {
    @Mock
    PizzaDAO daoMock = Mockito.mock(PizzaDAO.class);
    @Mock
    PizzaMapper mapperMock;
    @InjectMocks
    PizzaServiceImpl service;


    @Test
    void testAjouterNull() {
        PizzaException pe = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(null));
        Assertions.assertEquals("La pizza ne peux pas être nul", pe.getMessage());
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

    @Test
    void testSupprimerIdExistePas(){
        Mockito.when(daoMock.findById(1)).thenReturn(Optional.empty());
        EntityNotFoundException pe = Assertions.assertThrows(EntityNotFoundException.class, ()->service.supprimer(1));
        Assertions.assertEquals("L'id n'existe pas", pe.getMessage());
    }

    @Test
    void testSupprimerOK(){
        Pizza pizzaASupprimer = creerPizza();
        Mockito.when(daoMock.findById(1)).thenReturn(Optional.of(pizzaASupprimer));
        Pizza pizzaApresSupprimer= new Pizza("Margherita",tarifDefini(),listIngredients(),false);
        pizzaApresSupprimer.setId(1);
        Pizza pizzaEnreg= new Pizza("Margherita",tarifDefini(),listIngredients(),false);
        pizzaEnreg.setId(1);
        Mockito.when(daoMock.save(pizzaApresSupprimer)).thenReturn(pizzaEnreg);
        Mockito.when(mapperMock.toPizzaResponseDto(pizzaEnreg)).thenReturn(creerPizzaResponseDto());
        Assertions.assertEquals(creerPizzaResponseDto(),service.supprimer(1));
        Mockito.verify(daoMock).save(pizzaApresSupprimer);
    }


    @Test
    void testTrouverTousAvecBaseVide(){
        Mockito.when(daoMock.findAll()).thenReturn(List.of());
        Assertions.assertEquals(List.of(), service.trouverTous());
    }
    @Test
    void testTrouverTousOK(){
        Pizza pizza1 = creerPizza();
        Pizza pizza2 = creerPizza2();
        List<Pizza> listePizza = List.of(pizza1, pizza2);
        PizzaResponseDto pizzaResponseDto1 = creerPizzaResponseDto();
        PizzaResponseDto pizzaResponseDto2 = creerPizzaResponseDto2();
        List<PizzaResponseDto> listePizzaDto = List.of(pizzaResponseDto1, pizzaResponseDto2);
        Mockito.when(daoMock.findAll()).thenReturn(listePizza);
        Mockito.when(mapperMock.toPizzaResponseDto(listePizza.getFirst())).thenReturn(listePizzaDto.getFirst());
        Mockito.when(mapperMock.toPizzaResponseDto(listePizza.get(1))).thenReturn(listePizzaDto.get(1));
        Assertions.assertEquals(listePizzaDto, service.trouverTous());
    }

    @Test
    void testFiltrerParIdAvecIdExistePas(){
        Mockito.when(daoMock.findById(1)).thenReturn(Optional.empty());
        EntityNotFoundException pe = Assertions.assertThrows(EntityNotFoundException.class,()->service.filtrerParId(1));
        Assertions.assertEquals("L'id n'existe pas", pe.getMessage());
    }

    @Test
    void testFiltrerParIdOk(){
        Pizza pizza = creerPizza();
        PizzaResponseDto pizzaResponseDto = creerPizzaResponseDto();
        Mockito.when(daoMock.findById(1)).thenReturn(Optional.of(pizza));
        Mockito.when(mapperMock.toPizzaResponseDto(pizza)).thenReturn(pizzaResponseDto);
        Assertions.assertEquals(pizzaResponseDto, service.filtrerParId(1));
    }


    @Test
    void testFiltrerParNomAvecNomExistePas(){
        Mockito.when(daoMock.findByNomIgnoreCase("Hawaïenne")).thenReturn(Optional.empty());
        EntityNotFoundException entityNotFoundException = Assertions.assertThrows(EntityNotFoundException.class,()->service.filtrerParNom("Hawaïenne"));
        Assertions.assertEquals( "Pizza avec ce nom n'existe pas", entityNotFoundException.getMessage());
    }

    @Test
    void testFiltrerParNomOK(){
        Pizza pizzaTrouve = creerPizza();
        PizzaResponseDto pizzaResponseDto = creerPizzaResponseDto();
        Mockito.when(daoMock.findByNomIgnoreCase("Margherita")).thenReturn(Optional.of(pizzaTrouve));
        Mockito.when(mapperMock.toPizzaResponseDto(pizzaTrouve)).thenReturn(pizzaResponseDto);
        Assertions.assertEquals(pizzaResponseDto, service.filtrerParNom("Margherita"));
    }

    @Test
    void testFiltrerParIngredientOK(){
        Pizza pizzaTrouve = creerPizza();
        Pizza pizzaTrouve2 = creerPizza2();
        List<Pizza> listePizza = List.of(pizzaTrouve, pizzaTrouve2);
        PizzaResponseDto pizzaResponseDto = creerPizzaResponseDto();
        PizzaResponseDto pizzaResponseDto1 = creerPizzaResponseDto2();
        List<PizzaResponseDto> listePizzaDto = List.of(pizzaResponseDto1, pizzaResponseDto);
        Mockito.when(daoMock.findByIngredientNom("Tomate")).thenReturn(listePizza);
        Mockito.when(mapperMock.toPizzaResponseDto(listePizza.getFirst())).thenReturn(listePizzaDto.getFirst());
        Mockito.when(mapperMock.toPizzaResponseDto(listePizza.get(1))).thenReturn(listePizzaDto.get(1));
        Assertions.assertEquals(listePizzaDto, service.filtrerParIngredient("Tomate"));
    }

    @Test
    void testModifierNull(){
        PizzaException pe = Assertions.assertThrows(PizzaException.class,()->service.modifier(1,null));
        Assertions.assertEquals("La pizza ne peux pas être nul", pe.getMessage());
    }
    @Test
    void testModifierIdNonExistante(){
        Mockito.when(daoMock.findById(1)).thenReturn(Optional.empty());
        EntityNotFoundException pe = Assertions.assertThrows(EntityNotFoundException.class,()-> service.modifier(1, new PizzaRequestDto("  " ,tarifDefini(),List.of(1,2),true)));
        Assertions.assertEquals("L'id n'existe pas", pe.getMessage());
    }

    @Test
    void testModifierNomBlank(){
        Pizza nouvellePizza = new Pizza("  ",tarifDefini(),listIngredients(),true);
        PizzaRequestDto pizzaRequestDto = new PizzaRequestDto("  ",tarifDefini(),List.of(1,2),true);
        Mockito.when(daoMock.findById(1)).thenReturn(Optional.of(nouvellePizza));
        Mockito.when(mapperMock.toPizza(pizzaRequestDto)).thenReturn(nouvellePizza);
        PizzaException pe = Assertions.assertThrows(PizzaException.class, ()->service.modifier(1,pizzaRequestDto));
        Assertions.assertEquals("Le nom ne peux pas être vide", pe.getMessage());
    }

    @Test
    void testModifierOk(){
        Pizza pizzaAModifier = creerPizza();
        PizzaRequestDto pizzaRequestDto = new PizzaRequestDto("Quatres fromages", tarifDefini(), List.of(1,2),true);
        Pizza pizzaEnreg = creerPizza();
        pizzaEnreg.setId(1);
        PizzaResponseDto pizzaResponseDto = creerPizzaResponseDto();
        Mockito.when(daoMock.findById(1)).thenReturn(Optional.of(pizzaAModifier));
        Mockito.when(mapperMock.toPizza(pizzaRequestDto)).thenReturn(pizzaEnreg);
        Mockito.when(mapperMock.toPizzaResponseDto(pizzaEnreg)).thenReturn(pizzaResponseDto);
        Mockito.when(daoMock.save(pizzaAModifier)).thenReturn(pizzaEnreg);
        Assertions.assertEquals(pizzaResponseDto,service.modifier(1, pizzaRequestDto));
        Mockito.verify(daoMock).save(pizzaAModifier);
    }


    /* *********************************************** *
     *                                                 *
     *                 methodes privées                *
     *                                                 *
     * *********************************************** *
     */

    private static List<Ingredient> listIngredients(){
        Ingredient ingredient1 = new Ingredient("Tomate",12,true);
        Ingredient ingredient2 = new Ingredient("Olive",30,true);
        List<Ingredient> listeIngredient= List.of(ingredient1,ingredient2);
        return listeIngredient;
    }

    private static List<Ingredient> listeIngredientVide(){
        return Collections.emptyList();
    }

    private static HashMap<Taille, Double> tarifDefini(){
        HashMap<Taille, Double> map = new HashMap<>();
        map.put(Taille.PETITE, 7.0);
        map.put(Taille.MOYENNE,12.0);
        map.put(Taille.GRANDE,17.5);
        return map;
    }

    private static Pizza creerPizza(){
        return new Pizza("Margherita",tarifDefini(),listIngredients(),true);
    }
    private static Pizza creerPizza2(){return new Pizza("Quatre Fromages",tarifDefini(),listIngredients(),true);}

    private static PizzaResponseDto creerPizzaResponseDto(){
        return new PizzaResponseDto(1,"Margherita",tarifDefini(),List.of("Tomate", "Olive"),true);
    }

    private static PizzaResponseDto creerPizzaResponseDto2(){
        return new PizzaResponseDto(2,"Quatre Fromages",tarifDefini(),List.of("Tomate", "Olive"),true);
    }

}

