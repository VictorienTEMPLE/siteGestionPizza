package com.accenture.controller;

import com.accenture.repository.entity.Ingredient;
import com.accenture.service.dto.IngredientRequestDto;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class IngredientControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testPostIngredientAvecObjet() throws Exception{
        IngredientRequestDto ingredientdto = new IngredientRequestDto("Fromage",12,true);
        mockMvc.perform(MockMvcRequestBuilders.post("/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ingredientdto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(Matchers.not(0)))
                .andExpect(jsonPath("$.nom").value("Fromage"));
    }

    @Test
    void testTrouverOk() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/ingredients"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    void testModofierIdExistePas() throws Exception {
        IngredientRequestDto tomate = new IngredientRequestDto("Tomate",10,true);
        mockMvc.perform(MockMvcRequestBuilders.patch("/ingredients/56")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tomate)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("Erreur fonctionnelle"))
                .andExpect(jsonPath("$.message").value("L'id n'existe pas"));
    }

    @Test
    void testModifierOk() throws Exception{
        IngredientRequestDto tomate = new IngredientRequestDto("Tomate",10,true);
        mockMvc.perform(MockMvcRequestBuilders.patch("/ingredients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tomate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nom").value("Tomate"))
                .andExpect(jsonPath("$.quantiteEnStock").value(10))
                .andExpect(jsonPath("$.enStock").value(true));
    }


    @Test
    void testPostIngredientPasOk() throws Exception{
        Ingredient ingredient = new Ingredient(null,12,true);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/ingredients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ingredient)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("Erreur validation"))
                .andExpect(jsonPath("$.message").value("Le nom ne peux pas être null, ou vide"));

    }

    @Test
    void testGetIngredientOk() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/ingredients"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

}

