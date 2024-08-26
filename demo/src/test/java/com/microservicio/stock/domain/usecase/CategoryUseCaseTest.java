package com.microservicio.stock.domain.usecase;


import com.microservicio.stock.domain.exception.custom.CategoryAlreadyExistsException;
import com.microservicio.stock.domain.exception.custom.ValidationExceptions;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.spi.ICategoryPersistencePort;
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


class CategoryUseCaseTest {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 90;

    @Mock
    private ICategoryPersistencePort iCategoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should call the persistence port when the category is validated")
    void saveCategory_ShouldCallPersistencePort_WhenCategoryIsValid() {
        // Dado
        Long id = 1L;
        String name = "Electronics";
        String description = "Devices and gadgets";
        Category category = new Category(id, name, description);

        // Cuando
        categoryUseCase.saveCategory(category);

        // Entonces
        verify(iCategoryPersistencePort, times(1)).saveCategory(category);
    }

    @Test
    @DisplayName("Should save category when it does not already exist")
    void testSaveCategoryWhenCategoryDoesNotExist() {
        Category category = new Category(1L,"Electronics","Electronics");

        when(iCategoryPersistencePort.existsByName(category.getName())).thenReturn(false);
        when(iCategoryPersistencePort.saveCategory(any(Category.class))).thenReturn(category);

        Category savedCategory = categoryUseCase.saveCategory(category);

        assertNotNull(savedCategory);
        assertEquals(category.getName(), savedCategory.getName());
        verify(iCategoryPersistencePort, times(1)).existsByName(category.getName());
        verify(iCategoryPersistencePort, times(1)).saveCategory(category);
    }

    @Test
    @DisplayName("Should throw exception when trying to save an existing category")
    void testSaveCategoryWhenCategoryAlreadyExists() {
        Category category = new Category(1L,"Electronics","Electronics");


        when(iCategoryPersistencePort.existsByName(category.getName())).thenReturn(true);

        CategoryAlreadyExistsException exception = assertThrows(CategoryAlreadyExistsException.class, () -> {
            categoryUseCase.saveCategory(category);
        });

        assertEquals("La categoría ya existe", exception.getMessage());
        verify(iCategoryPersistencePort, times(1)).existsByName(category.getName());
        verify(iCategoryPersistencePort, never()).saveCategory(any(Category.class));
    }

    @Test
    @DisplayName("Should return true if category name exists")
    void testExistsByName() {
        String name = "Electronics";
        when(iCategoryPersistencePort.existsByName(name)).thenReturn(true);

        boolean exists = categoryUseCase.existsByName(name);

        assertTrue(exists);
        verify(iCategoryPersistencePort, times(1)).existsByName(name);
    }

    @Test
    @DisplayName("Should return false if category name does not exist")
    void testExistsByNameWhenCategoryDoesNotExist() {
        String name = "NonExistentCategory";
        when(iCategoryPersistencePort.existsByName(name)).thenReturn(false);

        boolean exists = categoryUseCase.existsByName(name);

        assertFalse(exists);
        verify(iCategoryPersistencePort, times(1)).existsByName(name);
    }

    @Test
    @DisplayName("Should return a list of categories")
    void testGetCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L,"Electronics","Electronics"));
        when(iCategoryPersistencePort.getCategories(0, 10, "name", true)).thenReturn(categories);

        List<Category> retrievedCategories = categoryUseCase.getCategories(0, 10, "name", true);

        assertNotNull(retrievedCategories);
        assertEquals(1, retrievedCategories.size());
        verify(iCategoryPersistencePort, times(1)).getCategories(0, 10, "name", true);
    }

    @Test
    @DisplayName("Should throw exception when category name is empty")
    void testValidateCategoryWhenNameIsEmpty() {
        Category category = new Category(1L, "", "Valid description");

        ValidationExceptions exception = assertThrows(ValidationExceptions.class, () -> {
            categoryUseCase.validateCategory(category);
        });

        assertEquals("The name cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when category name exceeds max length")
    void testValidateCategoryWhenNameExceedsMaxLength() {
        String longName = "a".repeat(MAX_NAME_LENGTH + 1);
        Category category = new Category(1L, longName, "Valid description");

        ValidationExceptions exception = assertThrows(ValidationExceptions.class, () -> {
            categoryUseCase.validateCategory(category);
        });

        assertEquals("The name must not exceed 50 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when category description is empty")
    void testValidateCategoryWhenDescriptionIsEmpty() {
        Category category = new Category(1L, "Valid name", "");

        ValidationExceptions exception = assertThrows(ValidationExceptions.class, () -> {
            categoryUseCase.validateCategory(category);
        });

        assertEquals("The description cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when category description exceeds max length")
    void testValidateCategoryWhenDescriptionExceedsMaxLength() {
        String longDescription = "a".repeat(MAX_DESCRIPTION_LENGTH + 1);
        Category category = new Category(1L, "Valid name", longDescription);

        ValidationExceptions exception = assertThrows(ValidationExceptions.class, () -> {
            categoryUseCase.validateCategory(category);
        });

        assertEquals("The description must not exceed 120 characters", exception.getMessage());
    }

    @Test
    @DisplayName("Should pass validation for valid category")
    void testValidateCategoryWhenCategoryIsValid() {
        Category category = new Category(1L, "Valid name", "Valid description");

        assertDoesNotThrow(() -> categoryUseCase.validateCategory(category));
    }


}
