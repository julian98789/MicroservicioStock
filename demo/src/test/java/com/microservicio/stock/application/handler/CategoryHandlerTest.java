package com.microservicio.stock.application.handler;

import com.microservicio.stock.application.dto.categorydto.CategoriRequest;
import com.microservicio.stock.application.dto.categorydto.CategoryResponse;
import com.microservicio.stock.application.handler.categoryhandler.CategoryHandler;
import com.microservicio.stock.application.mapper.categorymapper.ICategoryRequestMapper;
import com.microservicio.stock.application.mapper.categorymapper.ICategoryResponseMapper;
import com.microservicio.stock.domain.api.ICategoryServicePort;
import com.microservicio.stock.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CategoryHandlerTest {

    @Mock
    private ICategoryServicePort iCategoryServicePort;

    @Mock
    private ICategoryRequestMapper iCategoryRequestMapper;

    @Mock
    private ICategoryResponseMapper iCategoryResponseMapper;

    @InjectMocks
    private CategoryHandler categoryHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debe convertir CategoriRequest a Categori y llamar al servicio de dominio")
    void saveCategory_ValidRequest_ReturnsResponse() {
        // Dado
        CategoriRequest request = new CategoriRequest();
        request.setName("Electronics");
        request.setDescription("Devices and gadgets");

        Category category = new Category(1L, "Electronics", "Devices and gadgets");
        Category savedCategory = new Category(1L, "Electronics", "Devices and gadgets");

        CategoryResponse response = new CategoryResponse();
        response.setName("Electronics");
        response.setDescription("Devices and gadgets");

        // Cuando
        when(iCategoryRequestMapper.categoryRequestToCategory(request)).thenReturn(category);
        when(iCategoryServicePort.saveCategory(category)).thenReturn(savedCategory);
        when(iCategoryResponseMapper.categoryResponseToResponse(savedCategory)).thenReturn(response);


        CategoryResponse result = categoryHandler.saveCategory(request);

        // Entonces
        assertEquals(response.getName(), result.getName());
        assertEquals(response.getDescription(), result.getDescription());
    }
}