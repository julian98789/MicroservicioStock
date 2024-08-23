package com.MicroservicioStock.demo.infrastructure.input.controller;

import com.MicroservicioStock.demo.application.dto.brandDto.BrandRequest;
import com.MicroservicioStock.demo.application.dto.brandDto.BrandResponse;
import com.MicroservicioStock.demo.application.handler.brandHandler.IBrandHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class BrandRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IBrandHandler iBrandHandler;

    @InjectMocks
    private BrandRestController brandRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(brandRestController).build();
    }

    @Test
    @DisplayName("Should successfully save a brand and return HTTP 201")
    void testSaveBrand_Success() throws Exception {
        // Dado
        BrandRequest request = new BrandRequest();
        request.setName("Nueva marca");
        request.setDescription("Descripción de la nueva marca");

        BrandResponse response = new BrandResponse();
        response.setName("Nueva marca");

        // Cuando
        when(iBrandHandler.savedBrand(any(BrandRequest.class))).thenReturn(response);

        // Entonces
        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nueva marca\", \"description\":\"Descripción de la nueva marca\"}"))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Should return HTTP 400 when the request body is empty")
    void testSaveBrand_EmptyRequest() throws Exception {
        // Dado
        String emptyRequestBody = "";

        // Cuando
        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyRequestBody))
                // Entonces
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return HTTP 400 when the fields are empty")
    void testSaveBrand_EmptyFields() throws Exception {
        // Dado
        String requestBodyWithEmptyFields = "{ \"name\": \"\", \"description\": \"\" }";

        // Cuando
        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyWithEmptyFields))
                // Entonces
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return HTTP 400 when the name exceeds 50 characters")
    void testSaveBrand_NameTooLong() throws Exception {
        // Dado
        String longName = "A".repeat(51);
        String requestBodyWithLongName = String.format("{ \"name\": \"%s\", \"description\": \"Valid description\" }", longName);

        // Cuando
        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyWithLongName))
                // Entonces
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return HTTP 400 when the description exceeds 120 characters")
    void testSaveBrand_DescriptionTooLong() throws Exception {
        // Dado
        String longDescription = "A".repeat(121);
        String requestBodyWithLongDescription = String.format("{ \"name\": \"Valid name\", \"description\": \"%s\" }", longDescription);

        // Cuando
        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyWithLongDescription))
                // Entonces
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Should return HTTP 400 when the category already exists")
    void testSaveCategory_CategoryAlreadyExists() throws Exception {

        // Dado
        BrandRequest request = new BrandRequest();
        request.setName("Electronics");
        String requestJson = new ObjectMapper().writeValueAsString(request);

        // Cuando
        when(iBrandHandler.savedBrand(any(BrandRequest.class)))
                .thenThrow(new IllegalArgumentException("La marca ya existe"));

        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                // Entonces
                .andExpect(status().isBadRequest());

    }

    @Test
     void testGetBrands() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/brand")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "name")
                        .param("ascending", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

    }

}