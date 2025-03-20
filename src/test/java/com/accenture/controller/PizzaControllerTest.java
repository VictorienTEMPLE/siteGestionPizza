package com.accenture.controller;

import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.shared.Taille;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PizzaControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    private HashMap<Taille, Double> tarifDefini(){
        HashMap<Taille, Double> map = new HashMap<>();
        map.put(Taille.PETITE, 7.0);
        map.put(Taille.MOYENNE,12.0);
        map.put(Taille.GRANDE,17.5);
        return map;
    }

    @Test
    void testPostPizzaAvecObjet() throws Exception{
        PizzaRequestDto pizzaRequestDto = new PizzaRequestDto( "Quattre Fromages", tarifDefini(), List.of(1,2),true);

        mockMvc.perform(MockMvcRequestBuilders.post("/pizzas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pizzaRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(Matchers.not(0)))
                .andExpect(jsonPath("$.nom").value("Quattre Fromages"));
    }

    @Test
    void testPostPizzaPasOk() throws Exception{
        PizzaRequestDto pizzaRequestDto = new PizzaRequestDto( null, tarifDefini(), List.of(1,2),true);
        mockMvc.perform(MockMvcRequestBuilders.post("/pizzas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(pizzaRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("Erreur validation"))
                .andExpect(jsonPath("$.message").value("Le nom ne peux pas être nul, ou vide"));

    }

    @Test
    void testSupprimerIdExistePas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/pizzas/56"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("Erreur fonctionnelle"))
                .andExpect(jsonPath("$.message").value("L'id n'existe pas"));
    }

    @Test
    void testSupprimerOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/pizzas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.actif").value("false"));
    }

    @Test
    void testTrouverTousOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pizzas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testFiltrerParIdExistePas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pizzas/55"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("Erreur fonctionnelle"))
                .andExpect(jsonPath("$.message").value("L'id n'existe pas"));
    }

    @Test
    void testFiltrerParIdOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pizzas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nom").value("Margarita"));
    }

    @Test
    void testFiltrerParNomExistePas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pizzas/filtrer/chaussure"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("Erreur fonctionnelle"))
                .andExpect(jsonPath("$.message").value("Pizza avec ce nom n'existe pas"));
    }


    @Test
    void testFiltrerParNomOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pizzas/filtrer/Margarita"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nom").value("Margarita"));
    }

    @Test
    void testFiltrerParIngredientOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pizzas/filtrer/ingredient/Fromage"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }






}
