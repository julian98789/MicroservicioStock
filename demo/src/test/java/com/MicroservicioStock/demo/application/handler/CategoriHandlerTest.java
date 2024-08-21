package com.MicroservicioStock.demo.application.handler;

import com.MicroservicioStock.demo.application.dto.CategoriRequest;
import com.MicroservicioStock.demo.application.dto.CategoriResponse;
import com.MicroservicioStock.demo.application.mapper.CategoriRequestMappper;
import com.MicroservicioStock.demo.application.mapper.CategoriResponseMapper;
import com.MicroservicioStock.demo.domain.api.ICategoriServicePort;
import com.MicroservicioStock.demo.domain.model.Categori;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CategoriHandlerTest {

    @Mock
    private ICategoriServicePort iCategoriServicePort;

    @Mock
    private CategoriRequestMappper categoriRequestMappper;

    @Mock
    private CategoriResponseMapper categoriResponseMapper;

    @InjectMocks
    private CategoriHandler categoriHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debe convertir CategoriRequest a Categori y llamar al servicio de dominio")
    void saveCategori_ValidRequest_ReturnsResponse() {
        // Dado
        CategoriRequest request = new CategoriRequest();
        request.setName("Electronics");
        request.setDescription("Devices and gadgets");

        Categori categori = new Categori(1L, "Electronics", "Devices and gadgets");
        Categori savedCategori = new Categori(1L, "Electronics", "Devices and gadgets");

        CategoriResponse response = new CategoriResponse();
        response.setName("Electronics");
        response.setDescription("Devices and gadgets");

        // Cuando
        when(categoriRequestMappper.categoriRequestTocategori(request)).thenReturn(categori);
        when(iCategoriServicePort.saveCategori(categori)).thenReturn(savedCategori);
        when(categoriResponseMapper.categoriResponseToresponse(savedCategori)).thenReturn(response);


        CategoriResponse result = categoriHandler.saveCategori(request);

        // Entonces
        assertEquals(response.getName(), result.getName());
        assertEquals(response.getDescription(), result.getDescription());
    }
}