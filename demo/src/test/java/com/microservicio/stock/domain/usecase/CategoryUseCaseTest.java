package com.microservicio.stock.domain.usecase;


import com.microservicio.stock.domain.exception.custom.NameAlreadyExistsException;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.spi.ICategoryPersistencePort;
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
import static org.mockito.Mockito.*;


class CategoryUseCaseTest {


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

        NameAlreadyExistsException exception = assertThrows(NameAlreadyExistsException.class, () -> {
            categoryUseCase.saveCategory(category);
        });

        assertEquals(Util.CATEGORY_NAME_ALREADY_EXISTS, exception.getMessage());
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
    @DisplayName("Test listCategory returns a paginated result from the persistence port")
    void testListCategory() {
        Category category = new Category(1L, "categori Name", "categori Description");
        PaginatedResult<Category> paginatedResult = new PaginatedResult<>(
                List.of(category),
                0,
                10,
                1
        );

        when(iCategoryPersistencePort.getCategories(anyInt(), anyInt(), anyString(), anyBoolean())).thenReturn(paginatedResult);

        // Act
        PaginatedResult<Category> result = categoryUseCase.getCategories(0, 10, "name", true);

        // Assert
        assertEquals(1, result.getContent().size(), "The content size should be 1");
        assertEquals(category, result.getContent().get(0), "The article should match");
        assertEquals(0, result.getPageNumber(), "The page number should be 0");
        assertEquals(10, result.getPageSize(), "The page size should be 10");
        assertEquals(1, result.getTotalElements(), "The total elements should be 1");
    }





}
