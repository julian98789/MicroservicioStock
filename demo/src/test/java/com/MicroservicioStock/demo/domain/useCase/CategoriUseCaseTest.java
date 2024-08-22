package com.MicroservicioStock.demo.domain.useCase;


import com.MicroservicioStock.demo.domain.exception.custom.CategoriAlreadyExistsException;
import com.MicroservicioStock.demo.domain.model.Categori;
import com.MicroservicioStock.demo.domain.spi.ICategoriPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class CategoriUseCaseTest {

    @Mock
    private ICategoriPersistencePort iCategoriPersistencePort;

    @InjectMocks
    private CategoriUseCase categoriUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should call the persistence port when the category is validated")
    void saveCategori_ShouldCallPersistencePort_WhenCategoriIsValid() {
        // Dado
        Long id = 1L;
        String name = "Electronics";
        String description = "Devices and gadgets";
        Categori categori = new Categori(id, name, description);

        // Cuando
        categoriUseCase.saveCategori(categori);

        // Entonces
        verify(iCategoriPersistencePort, times(1)).saveCategori(categori);
    }

    @Test
    @DisplayName("Should save category when it does not already exist")
    void testSaveCategoriWhenCategoryDoesNotExist() {
        Categori categori = new Categori(1L,"Electronics","Electronics");

        when(iCategoriPersistencePort.existsByName(categori.getName())).thenReturn(false);
        when(iCategoriPersistencePort.saveCategori(any(Categori.class))).thenReturn(categori);

        Categori savedCategori = categoriUseCase.saveCategori(categori);

        assertNotNull(savedCategori);
        assertEquals(categori.getName(), savedCategori.getName());
        verify(iCategoriPersistencePort, times(1)).existsByName(categori.getName());
        verify(iCategoriPersistencePort, times(1)).saveCategori(categori);
    }

    @Test
    @DisplayName("Should throw exception when trying to save an existing category")
    void testSaveCategoriWhenCategoryAlreadyExists() {
        Categori categori = new Categori(1L,"Electronics","Electronics");


        when(iCategoriPersistencePort.existsByName(categori.getName())).thenReturn(true);

        CategoriAlreadyExistsException exception = assertThrows(CategoriAlreadyExistsException.class, () -> {
            categoriUseCase.saveCategori(categori);
        });

        assertEquals("La categoría ya existe", exception.getMessage());
        verify(iCategoriPersistencePort, times(1)).existsByName(categori.getName());
        verify(iCategoriPersistencePort, never()).saveCategori(any(Categori.class));
    }

    @Test
    @DisplayName("Should return true if category name exists")
    void testExistsByName() {
        String name = "Electronics";
        when(iCategoriPersistencePort.existsByName(name)).thenReturn(true);

        boolean exists = categoriUseCase.existsByName(name);

        assertTrue(exists);
        verify(iCategoriPersistencePort, times(1)).existsByName(name);
    }

    @Test
    @DisplayName("Should return false if category name does not exist")
    void testExistsByNameWhenCategoryDoesNotExist() {
        String name = "NonExistentCategory";
        when(iCategoriPersistencePort.existsByName(name)).thenReturn(false);

        boolean exists = categoriUseCase.existsByName(name);

        assertFalse(exists);
        verify(iCategoriPersistencePort, times(1)).existsByName(name);
    }

    @Test
    @DisplayName("Should return a list of categories")
    void testGetCategories() {
        List<Categori> categories = new ArrayList<>();
        categories.add(new Categori(1L,"Electronics","Electronics"));
        when(iCategoriPersistencePort.getCategories(0, 10, "name", true)).thenReturn(categories);

        List<Categori> retrievedCategories = categoriUseCase.getCategories(0, 10, "name", true);

        assertNotNull(retrievedCategories);
        assertEquals(1, retrievedCategories.size());
        verify(iCategoriPersistencePort, times(1)).getCategories(0, 10, "name", true);
    }



}
