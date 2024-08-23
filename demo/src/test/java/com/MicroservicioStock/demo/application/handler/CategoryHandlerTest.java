package com.MicroservicioStock.demo.application.handler;

import com.MicroservicioStock.demo.application.dto.categoryDto.CategoriRequest;
import com.MicroservicioStock.demo.application.dto.categoryDto.CategoryResponse;
import com.MicroservicioStock.demo.application.handler.categoryHandler.CategoryHandler;
import com.MicroservicioStock.demo.application.mapper.categoryMappper.ICategoryRequestMappper;
import com.MicroservicioStock.demo.application.mapper.categoryMappper.ICategoryResponseMapper;
import com.MicroservicioStock.demo.domain.api.ICategoryServicePort;
import com.MicroservicioStock.demo.domain.model.Category;
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
    private ICategoryRequestMappper iCategoryRequestMappper;

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
        when(iCategoryRequestMappper.categoryRequestToCategory(request)).thenReturn(category);
        when(iCategoryServicePort.saveCategory(category)).thenReturn(savedCategory);
        when(iCategoryResponseMapper.categoryResponseToResponse(savedCategory)).thenReturn(response);


        CategoryResponse result = categoryHandler.saveCategory(request);

        // Entonces
        assertEquals(response.getName(), result.getName());
        assertEquals(response.getDescription(), result.getDescription());
    }
}