package com.accenture.controller;

import com.accenture.service.IngredientService;
import com.accenture.service.dto.IngredientResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(controllers = IngredientController.class)
public class TestIngredientController {
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IngredientService ingredientService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testPostIngredientAvecObjet() throws Exception{
        IngredientResponseDto ingredientdto = new IngredientResponseDto(1,"Fromage",12,true);
        mockMvc.perform(MockMvcRequestBuilders.post("/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ingredientdto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(Matchers.not(0)))
                .andExpect(jsonPath("$.nom").value("Fromage"));
    }
}
