package com.MicroservicioStock.demo.infrastructure.input.controller;

import com.MicroservicioStock.demo.application.dto.CategoriRequest;
import com.MicroservicioStock.demo.application.dto.CategoriResponse;
import com.MicroservicioStock.demo.application.handler.CategoriHandler;
import com.MicroservicioStock.demo.application.handler.ICategoriHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriRestController.class)
class CategoriRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CategoriHandler categoriHandler;

    @MockBean
    private ICategoriHandler iCategoriHandler;

    @InjectMocks
    private CategoriRestController categoriRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoriRestController).build();
    }


    @Test
    @DisplayName("Should successfully save a category and return HTTP 201")
    public void testSaveCategori_Success() throws Exception {
        // Dado
        CategoriRequest request = new CategoriRequest();
        request.setName("Nueva Categoría");
        request.setDescription("Descripción de la nueva categoría");

        CategoriResponse response = new CategoriResponse();
        response.setName("Nueva Categoría");

        // Cuando
        when(iCategoriHandler.saveCategori(any(CategoriRequest.class))).thenReturn(response);

        // Entonces
        mockMvc.perform(post("/categori")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nueva Categoría\", \"description\":\"Descripción de la nueva categoría\"}")) // JSON del CategoriRequest
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Should return HTTP 400 when the request body is empty")
    void testSaveCategori_EmptyRequest() throws Exception {
        // Dado
        String emptyRequestBody = "";

        // Cuando
        mockMvc.perform(post("/categori")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyRequestBody))
                // Entonces
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return HTTP 400 when the fields are empty")
    void testSaveCategori_EmptyFields() throws Exception {
        // Dado
        String requestBodyWithEmptyFields = "{ \"name\": \"\", \"description\": \"\" }";

        // Cuando
        mockMvc.perform(post("/categori")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyWithEmptyFields))
                // Entonces
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return HTTP 400 when the name exceeds 50 characters")
    void testSaveCategori_NameTooLong() throws Exception {
        // Dado
        String longName = "A".repeat(51);
        String requestBodyWithLongName = String.format("{ \"name\": \"%s\", \"description\": \"Valid description\" }", longName);

        // Cuando
        mockMvc.perform(post("/categori")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyWithLongName))
                // Entonces
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return HTTP 400 when the description exceeds 90 characters")
    void testSaveCategori_DescriptionTooLong() throws Exception {
        // Dado
        String longDescription = "A".repeat(91);
        String requestBodyWithLongDescription = String.format("{ \"name\": \"Valid name\", \"description\": \"%s\" }", longDescription);

        // Cuando
        mockMvc.perform(post("/categori")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyWithLongDescription))
                // Entonces
                .andExpect(status().isBadRequest());

    }
    @Test
    @DisplayName("Should return HTTP 400 when the category already exists")
    public void testSaveCategori_CategoryAlreadyExists() throws Exception {

        // Dado
        CategoriRequest request = new CategoriRequest();
        request.setName("Electronics");
        String requestJson = new ObjectMapper().writeValueAsString(request);

        // Cuando
        when(iCategoriHandler.saveCategori(any(CategoriRequest.class)))
                .thenThrow(new IllegalArgumentException("La categoría ya existe"));

        mockMvc.perform(post("/categori")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                // Entonces
                .andExpect(status().isBadRequest());

    }


}
