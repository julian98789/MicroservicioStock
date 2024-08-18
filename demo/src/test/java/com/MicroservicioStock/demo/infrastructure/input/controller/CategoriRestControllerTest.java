package com.MicroservicioStock.demo.infrastructure.input.controller;

import com.MicroservicioStock.demo.application.dto.CategoriRequest;
import com.MicroservicioStock.demo.application.dto.CategoriResponse;
import com.MicroservicioStock.demo.application.handler.CategotiHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;

class CategoriRestControllerTest {
    @Mock
    private CategotiHandler categoriHandler;

    @InjectMocks
    private CategoriRestController categoriRestController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debe manejar la solicitud de guardar categoría y devolver la respuesta esperada con estado 201")
    void saveCategori_ShouldReturnCreatedStatus_WhenCategoryIsValid() {
        // Dado
        CategoriRequest request = new CategoriRequest();
        request.setName("Electronics");
        request.setDescription("Devices and gadgets");

        CategoriResponse response = new CategoriResponse();
        response.setName("Electronics");
        response.setDescription("Devices and gadgets");

        when(categoriHandler.saveCategori(request)).thenReturn(response);

        // Cuando
        ResponseEntity<CategoriResponse> result = categoriRestController.saveCategori(request);

        // Entonces
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }


    @Test
    @DisplayName("Debe devolver un estado HTTP 400 cuando no se envía ningún campo en la solicitud")
    void saveCategori_ShouldReturnBadRequestStatus_WhenNoContentIsSent() throws Exception {
        // Dado: una solicitud vacía
        String emptyRequest = ""; // Representa una solicitud vacía

        // Cuando: se realiza una solicitud POST sin contenido
        mockMvc.perform(MockMvcRequestBuilders.post("/categori")
                        .content(emptyRequest)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // Entonces: se espera un estado HTTP 400 (Bad Request)
    }


}
