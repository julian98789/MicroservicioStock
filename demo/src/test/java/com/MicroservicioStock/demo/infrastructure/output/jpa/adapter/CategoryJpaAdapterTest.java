package com.MicroservicioStock.demo.infrastructure.output.jpa.adapter;

import com.MicroservicioStock.demo.domain.model.Category;
import com.MicroservicioStock.demo.domain.exception.custom.CategoryAlreadyExistsException;
import com.MicroservicioStock.demo.infrastructure.output.jpa.entity.CategoryEntity;
import com.MicroservicioStock.demo.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.MicroservicioStock.demo.infrastructure.output.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryJpaAdapterTest {
    private final ICategoryRepository iCategoryRepository = Mockito.mock(ICategoryRepository.class);
    private final CategoryEntityMapper CategoryEntityMapper = Mockito.mock(CategoryEntityMapper.class);
    private final CategoryJpaAdapter categoryJpaAdapter = new CategoryJpaAdapter(iCategoryRepository, CategoryEntityMapper);

    @Test
    @DisplayName("Successfully save category")
    void testSaveCategory_Success() {
        // Dado
        Category category = new Category(1L, "Electronics", "Devices and gadgets");

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Electronics");
        categoryEntity.setDescription("Devices and gadgets");

        when(CategoryEntityMapper.toEntity(category)).thenReturn(categoryEntity);
        when(iCategoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(CategoryEntityMapper.toCategory(categoryEntity)).thenReturn(category);

        // Cuando
        Category savedCategory = categoryJpaAdapter.saveCategory(category);

        // Entonces
        assertNotNull(savedCategory);
        assertEquals("Electronics", savedCategory.getName());
        assertEquals("Devices and gadgets", savedCategory.getDescription());
        verify(iCategoryRepository, times(1)).save(categoryEntity);
    }

    @Test
    @DisplayName("Throw exception if category already exists")
    void testSaveCategory_CategoryAlreadyExists() {
        // Dado
        Category category = new Category(1L, "Electronics", "Devices and gadgets");

        when(iCategoryRepository.findByName(category.getName())).thenReturn(Optional.of(new CategoryEntity()));

        // Cuando y Entonces
        assertThrows(CategoryAlreadyExistsException.class, () -> {
            if (categoryJpaAdapter.existsByName(category.getName())) {
                throw new CategoryAlreadyExistsException("La categoría ya existe");
            }
        });
    }
}
