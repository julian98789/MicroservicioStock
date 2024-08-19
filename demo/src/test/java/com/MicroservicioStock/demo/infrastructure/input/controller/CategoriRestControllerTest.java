package com.MicroservicioStock.demo.infrastructure.input.controller;

import com.MicroservicioStock.demo.application.dto.CategoriRequest;
import com.MicroservicioStock.demo.application.dto.CategoriResponse;
import com.MicroservicioStock.demo.application.handler.CategotiHandler;
import com.MicroservicioStock.demo.application.handler.ICategoriHandler;
import com.MicroservicioStock.demo.domain.model.Categori;
import com.MicroservicioStock.demo.infrastructure.exception.custom.CategoriAlreadyExistsException;
import com.MicroservicioStock.demo.infrastructure.output.jpa.entity.CategoriEntity;
import com.MicroservicioStock.demo.infrastructure.output.jpa.repository.ICategoriRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CategoriRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ICategoriRepository iCategoriRepository;

    @Mock
    private CategotiHandler categoriHandler;

    @InjectMocks
    private CategoriRestController categoriRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoriRestController).build();
    }
    @Test
    @DisplayName("Deberia retornar HTTP 201 cuando los datos ingresados sean validos")
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
    @DisplayName("Deberia retornar HTTP 400 cuando el cuerpo de la solicitud este vacio")
    void shouldReturnBadRequestWhenRequestBodyIsEmpty() throws Exception {
        // Dado un cuerpo vacio
        String emptyRequestBody = "";

        // Cuando se hace una solicitud POST con un cuerpo vacio
        mockMvc.perform(MockMvcRequestBuilders.post("/categori")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyRequestBody))
                // Entonces se espera un estado 400 Bad Request
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deberia retornar HTTP 400 cuando los campos esten vacios")
    void shouldReturnBadRequestWhenFieldsAreEmpty() throws Exception {
        // Dado un CategoriRequest con campos vacios
        String requestBodyWithEmptyFields = "{ \"name\": \"\", \"description\": \"\" }";

        // Cuando se hace una solicitud POST con campos vacios
        mockMvc.perform(MockMvcRequestBuilders.post("/categori")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyWithEmptyFields))
                // Entonces se espera un estado 400 Bad Request
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deberia retornar HTTP 400 cuando el nombre exceda los 50 caracteres")
    void shouldReturnBadRequestWhenNameExceedsMaxLength() throws Exception {
        // Dado un CategoriRequest con un nombre de mas de 50 caracteres
        String longName = "A".repeat(51);  // Crea un nombre con 51 caracteres
        String requestBodyWithLongName = String.format("{ \"name\": \"%s\", \"description\": \"Valid description\" }", longName);

        // Cuando se hace una solicitud POST con un nombre largo
        mockMvc.perform(MockMvcRequestBuilders.post("/categori")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyWithLongName))
                // Entonces se espera un estado 400 Bad Request
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debería retornar HTTP 400 y cuando la descripción exceda los 90 caracteres")
    void shouldReturnBadRequestWhenDescriptionExceedsMaxLength() throws Exception {
        // Dado un CategoriRequest con una descripción de más de 90 caracteres
        String longDescription = "A".repeat(91);
        String requestBodyWithLongDescription = String.format("{ \"name\": \"Valid name\", \"description\": \"%s\" }", longDescription);

        // Cuando se hace una solicitud POST con una descripción larga
        mockMvc.perform(MockMvcRequestBuilders.post("/categori")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyWithLongDescription))
                // Entonces se espera un estado 400 Bad Request
                .andExpect(status().isBadRequest());

    }


}
