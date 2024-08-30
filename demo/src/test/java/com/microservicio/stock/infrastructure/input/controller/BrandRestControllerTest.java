package com.microservicio.stock.infrastructure.input.controller;

import com.microservicio.stock.application.dto.branddto.BrandRequest;
import com.microservicio.stock.application.dto.branddto.BrandResponse;
import com.microservicio.stock.application.handler.brandhandler.IBrandHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.stock.domain.util.pagination.PaginatedResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    @DisplayName("Should return a paginated list of brands successfully")
     void testListBrand() throws Exception {

        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setId(1L);
        brandResponse.setName("Brand 1");
        brandResponse.setDescription("Description");

        PaginatedResult<BrandResponse> paginatedResultResponse = new PaginatedResult<>(
                Collections.singletonList(brandResponse),
                0,
                10,
                1L
        );

        when(iBrandHandler.getBrands(anyInt(), anyInt(), anyString(), anyBoolean())).thenReturn(paginatedResultResponse);

        mockMvc.perform(get("/brand")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "name")
                        .param("ascending", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Brand 1"))
                .andExpect(jsonPath("$.content[0].description").value("Description"))
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.totalElements").value(1));
    }



}