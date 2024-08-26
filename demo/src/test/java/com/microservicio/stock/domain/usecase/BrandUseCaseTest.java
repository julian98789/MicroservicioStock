package com.microservicio.stock.domain.usecase;

import com.microservicio.stock.domain.exception.custom.BrandAlreadyExistsException;
import com.microservicio.stock.domain.exception.custom.ValidationExceptions;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BrandUseCaseTest {


    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 120;

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

        BrandAlreadyExistsException exception = assertThrows(BrandAlreadyExistsException.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        // Entonces
        assertEquals("La marca ya existe", exception.getMessage());
        verify(iBrandPersistencePort, times(1)).existsByName(brand.getName());
        verify(iBrandPersistencePort, never()).saveBrand(any(Brand.class));
    }

    @Test
    @DisplayName("Should return a list of brand")
    void testGetBrands() {
        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(1L,"Electronics","Electronics"));
        when(iBrandPersistencePort.getBrands(0, 10, "name", true)).thenReturn(brands);

        List<Brand> retrievedBrands = brandUseCase.getBrands(0, 10, "name", true);

        assertNotNull(retrievedBrands);
        assertEquals(1, retrievedBrands.size());
        verify(iBrandPersistencePort, times(1)).getBrands(0, 10, "name", true);
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
    @DisplayName("Should throw exception when brand name is empty")
    void testValidateBrandWhenNameIsEmpty() {
        // Dado
        Brand brand = new Brand(1L, "", "Sportswear brand");

        // Cuando
        ValidationExceptions exception = assertThrows(ValidationExceptions.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        // Entonces
        assertEquals("The name cannot be empty", exception.getMessage());
        verify(iBrandPersistencePort, never()).existsByName(anyString());
        verify(iBrandPersistencePort, never()).saveBrand(any(Brand.class));
    }

    @Test
    @DisplayName("Should throw exception when brand name exceeds max length")
    void testValidateBrandWhenNameExceedsMaxLength() {
        // Dado
        String longName = "a".repeat(MAX_NAME_LENGTH + 1);
        Brand brand = new Brand(1L, longName, "Sportswear brand");

        // Cuando
        ValidationExceptions exception = assertThrows(ValidationExceptions.class, () -> {
            brandUseCase.validateBrand(brand);
        });

        assertEquals("The name must not exceed 50 characters", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when brand description is empty")
    void testValidateBrandWhenDescriptionIsEmpty() {
        // Dado
        Brand brand = new Brand(1L, "Nike", "");

        // Cuando
        ValidationExceptions exception = assertThrows(ValidationExceptions.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        // Entonces
        assertEquals("The description cannot be empty", exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception when brand description exceeds max length")
    void testValidateBrandWhenDescriptionExceedsMaxLength() {
        // Dado
        String longDescription = "a".repeat(MAX_DESCRIPTION_LENGTH + 1);
        Brand brand = new Brand(1L, "Nike", longDescription);

        // Cuando
        ValidationExceptions exception = assertThrows(ValidationExceptions.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        // Entonces
        assertEquals("The description must not exceed 120 characters", exception.getMessage());
        verify(iBrandPersistencePort, never()).existsByName(anyString());
        verify(iBrandPersistencePort, never()).saveBrand(any(Brand.class));
    }




}
