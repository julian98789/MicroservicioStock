package com.microservicio.stock.infrastructure.input.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.stock.application.dto.articledto.ArticleRequest;
import com.microservicio.stock.application.dto.articledto.ArticleResponse;
import com.microservicio.stock.application.handler.articlehandler.IArticleHandler;
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

import java.math.BigDecimal;
import java.util.Arrays;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class ArticleRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IArticleHandler articleHandler;

    @InjectMocks
    private ArticleRestController articleRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(articleRestController).build();
    }


    @Test
    @DisplayName("Should successfully save an article and return HTTP 201")
    void testSaveArticle_Success() throws Exception {
        // Dado
        ArticleRequest request = new ArticleRequest();
        request.setName("Nuevo artículo");
        request.setDescription("Descripción detallada del artículo");
        request.setQuantity(10);
        request.setPrice(BigDecimal.valueOf(99.99));
        request.setBrandId(1L);
        request.setCategoryIds(Arrays.asList(1L, 2L, 3L));

        ArticleResponse response = new ArticleResponse();
        response.setName("Nuevo artículo");

        // Cuando
        when(articleHandler.saveArticle(any(ArticleRequest.class))).thenReturn(response);

        // Entonces
        mockMvc.perform(MockMvcRequestBuilders.post("/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Nuevo artículo"));
    }

    @Test
    @DisplayName("Should return HTTP 400 when the request body is empty")
    void testSaveArticle_EmptyRequest() throws Exception {
        // Dado
        String emptyRequestBody = "";

        // Cuando
        mockMvc.perform(MockMvcRequestBuilders.post("/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyRequestBody))
                // Entonces
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Should return HTTP 400 when the fields are empty")
    void testSaveArticle_EmptyFields() throws Exception {
        // Dado
        String requestBodyWithEmptyFields = "{ \"name\": \"\", \"description\": \"\", \"quantity\": 0, \"price\": 0.0, \"brandId\": null, \"categoryIds\": [] }";

        // Cuando
        mockMvc.perform(MockMvcRequestBuilders.post("/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyWithEmptyFields))
                // Entonces
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Should return HTTP 400 when the name exceeds 50 characters")
    void testSaveArticle_NameTooLong() throws Exception {
        // Dado
        String longName = "A".repeat(51);
        String requestBodyWithLongName = String.format("{ \"name\": \"%s\", \"description\": \"Valid description\", \"quantity\": 10, \"price\": 99.99, \"brandId\": 1, \"categoryIds\": [1, 2] }", longName);

        // Cuando
        mockMvc.perform(MockMvcRequestBuilders.post("/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyWithLongName))
                // Entonces
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Should return HTTP 400 when the description exceeds 120 characters")
    void testSaveArticle_DescriptionTooLong() throws Exception {
        // Dado
        String longDescription = "A".repeat(121);
        String requestBodyWithLongDescription = String.format("{ \"name\": \"Valid name\", \"description\": \"%s\", \"quantity\": 10, \"price\": 99.99, \"brandId\": 1, \"categoryIds\": [1, 2] }", longDescription);

        // Cuando
        mockMvc.perform(MockMvcRequestBuilders.post("/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyWithLongDescription))
                // Entonces
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}