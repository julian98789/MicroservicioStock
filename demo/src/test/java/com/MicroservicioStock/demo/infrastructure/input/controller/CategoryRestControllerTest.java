package com.MicroservicioStock.demo.infrastructure.input.controller;

import com.MicroservicioStock.demo.application.dto.categoryDto.CategoriRequest;
import com.MicroservicioStock.demo.application.dto.categoryDto.CategoryResponse;
import com.MicroservicioStock.demo.application.handler.categoryHandler.CategoryHandler;
import com.MicroservicioStock.demo.application.handler.categoryHandler.ICategoryHandler;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CategoryRestController.class)
class CategoryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CategoryHandler categoryHandler;

    @MockBean
    private ICategoryHandler iCategoryHandler;

    @InjectMocks
    private CategoryRestController categoryRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryRestController).build();
    }


    @Test
    @DisplayName("Should successfully save a category and return HTTP 201")
    void testSaveCategory_Success() throws Exception {
        // Dado
        CategoriRequest request = new CategoriRequest();
        request.setName("Nueva Categoría");
        request.setDescription("Descripción de la nueva categoría");

        CategoryResponse response = new CategoryResponse();
        response.setName("Nueva Categoría");

        // Cuando
        when(iCategoryHandler.saveCategory(any(CategoriRequest.class))).thenReturn(response);

        // Entonces
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nueva Categoría\", \"description\":\"Descripción de la nueva categoría\"}")) // JSON del CategoriRequest
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Should return HTTP 400 when the request body is empty")
    void testSaveCategory_EmptyRequest() throws Exception {
        // Dado
        String emptyRequestBody = "";

        // Cuando
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyRequestBody))
                // Entonces
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return HTTP 400 when the fields are empty")
    void testSaveCategory_EmptyFields() throws Exception {
        // Dado
        String requestBodyWithEmptyFields = "{ \"name\": \"\", \"description\": \"\" }";

        // Cuando
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyWithEmptyFields))
                // Entonces
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return HTTP 400 when the name exceeds 50 characters")
    void testSaveCategory_NameTooLong() throws Exception {
        // Dado
        String longName = "A".repeat(51);
        String requestBodyWithLongName = String.format("{ \"name\": \"%s\", \"description\": \"Valid description\" }", longName);

        // Cuando
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyWithLongName))
                // Entonces
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return HTTP 400 when the description exceeds 90 characters")
    void testSaveCategory_DescriptionTooLong() throws Exception {
        // Dado
        String longDescription = "A".repeat(91);
        String requestBodyWithLongDescription = String.format("{ \"name\": \"Valid name\", \"description\": \"%s\" }", longDescription);

        // Cuando
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyWithLongDescription))
                // Entonces
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Should return HTTP 400 when the category already exists")
    void testSaveCategory_CategoryAlreadyExists() throws Exception {

        // Dado
        CategoriRequest request = new CategoriRequest();
        request.setName("Electronics");
        String requestJson = new ObjectMapper().writeValueAsString(request);

        // Cuando
        when(iCategoryHandler.saveCategory(any(CategoriRequest.class)))
                .thenThrow(new IllegalArgumentException("La categoría ya existe"));

        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                // Entonces
                .andExpect(status().isBadRequest());

    }


    @Test
    public void testGetCategories() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/category")
                        .param("page", "0")
                        .param("size", "2") // Solicita 2 elementos
                        .param("sort", "name")
                        .param("ascending", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

    }


}
