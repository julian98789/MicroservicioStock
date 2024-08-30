package com.microservicio.stock.domain.usecase;

import com.microservicio.stock.domain.exception.custom.NameAlreadyExistsException;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.spi.IBrandPersistencePort;
import com.microservicio.stock.domain.util.Util;
import com.microservicio.stock.domain.util.pagination.PaginatedResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BrandUseCaseTest {



    @Mock
    private IBrandPersistencePort iBrandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should save brand when it is valid and does not already exist")
    void saveBrand_ShouldCallPersistencePort_WhenBrandIsValidAndDoesNotExist() {
        // Dado
        Brand brand = new Brand(1L, "Nike", "Sportswear brand");

        // Cuando
        when(iBrandPersistencePort.existsByName(brand.getName())).thenReturn(false);
        when(iBrandPersistencePort.saveBrand(any(Brand.class))).thenReturn(brand);

        Brand savedBrand = brandUseCase.saveBrand(brand);

        // Entonces
        assertNotNull(savedBrand);
        assertEquals(brand.getName(), savedBrand.getName());
        verify(iBrandPersistencePort, times(1)).existsByName(brand.getName());
        verify(iBrandPersistencePort, times(1)).saveBrand(brand);
    }

    @Test
    @DisplayName("Should throw exception when trying to save an existing brand")
    void testSaveBrandWhenBrandAlreadyExists() {
        // Dado
        Brand brand = new Brand(1L, "Nike", "Sportswear brand");

        // Cuando
        when(iBrandPersistencePort.existsByName(brand.getName())).thenReturn(true);

        NameAlreadyExistsException exception = assertThrows(NameAlreadyExistsException.class, () -> {
            brandUseCase.saveBrand(brand);
        });


        // Entonces
        assertEquals(Util.BRAND_NAME_ALREADY_EXISTS, exception.getMessage());
        verify(iBrandPersistencePort, times(1)).existsByName(brand.getName());
        verify(iBrandPersistencePort, never()).saveBrand(any(Brand.class));
    }



    @Test
    @DisplayName("Should return true if brand name exists")
    void existsByName_ShouldReturnTrue_WhenBrandNameExists() {
        // Dado
        String name = "Nike";
        when(iBrandPersistencePort.existsByName(name)).thenReturn(true);

        // Cuando
        boolean exists = brandUseCase.existsByName(name);

        // Entonces
        assertTrue(exists);
        verify(iBrandPersistencePort, times(1)).existsByName(name);
    }

    @Test
    @DisplayName("Should return false if brand name does not exist")
    void existsByName_ShouldReturnFalse_WhenBrandNameDoesNotExist() {
        // Dado
        String name = "NonExistentBrand";
        when(iBrandPersistencePort.existsByName(name)).thenReturn(false);

        // Cuando
        boolean exists = brandUseCase.existsByName(name);

        // Entonces
        assertFalse(exists);
        verify(iBrandPersistencePort, times(1)).existsByName(name);
    }

    @Test
    @DisplayName("Test listBrand returns a paginated result from the persistence port")
    void testListBrand() {
        Brand brand = new Brand(1L, "brand Name", "brand Description");
        PaginatedResult<Brand> paginatedResult = new PaginatedResult<>(
                List.of(brand),
                0,
                10,
                1
        );

        when(iBrandPersistencePort.getBrands(anyInt(), anyInt(), anyString(), anyBoolean())).thenReturn(paginatedResult);


        PaginatedResult<Brand> result = brandUseCase.getBrands(0, 10, "name", true);


        assertEquals(1, result.getContent().size(), "The content size should be 1");
        assertEquals(brand, result.getContent().get(0), "The article should match");
        assertEquals(0, result.getPageNumber(), "The page number should be 0");
        assertEquals(10, result.getPageSize(), "The page size should be 10");
        assertEquals(1, result.getTotalElements(), "The total elements should be 1");
    }



}
