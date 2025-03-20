package com.accenture.controller;

import com.accenture.service.dto.ClientRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ClientControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testTrouverTousOk() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testFilterParIdExistePas() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/clients/50"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("Erreur fonctionnelle"))
                .andExpect(jsonPath("$.message").value("L'id n'existe pas"));
    }
    @Test
    void testAjouterClientInvalide() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto( "Emmanuel", null, true);
        mockMvc.perform(MockMvcRequestBuilders.post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("Erreur validation"))
                .andExpect(jsonPath("$.message").value("Le mail du client ne peut pas être nul ou vide"));
    }
    @Test
    void testAjouterOK() throws Exception{
        ClientRequestDto clientRequestDto = new ClientRequestDto("Emmanuel", "emmanuel@gmail.com", true);
        mockMvc.perform(MockMvcRequestBuilders.post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequestDto)))
                .andExpect(status().isCreated());
    }

}