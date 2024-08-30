package com.microservicio.stock.infrastructure.output.jpa.adapter;

import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.util.pagination.PaginatedResult;
import com.microservicio.stock.infrastructure.output.jpa.entity.CategoryEntity;
import com.microservicio.stock.infrastructure.output.jpa.mapper.ICategoryEntityMapper;
import com.microservicio.stock.infrastructure.output.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryJpaAdapterTest {
    @Mock
    private ICategoryRepository iCategoryRepository;

    @Mock
    private ICategoryEntityMapper iCategoryEntityMapper;

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save a category and return the saved category")
    void saveCategory_shouldSaveAndReturnCategory() {
        Long id = 1L;
        String name = "Test Category";
        String description = "Category Description";
        Category category = new Category(id, name, description);
        CategoryEntity categoryEntity = new CategoryEntity();
        CategoryEntity savedCategoryEntity = new CategoryEntity();

        when(iCategoryEntityMapper.toEntity(category)).thenReturn(categoryEntity);
        when(iCategoryRepository.save(categoryEntity)).thenReturn(savedCategoryEntity);
        when(iCategoryEntityMapper.toCategory(savedCategoryEntity))
                .thenReturn(new Category(id, name, description));

        Category result = categoryJpaAdapter.saveCategory(category);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(name, result.getName());
        assertEquals(description, result.getDescription());
        verify(iCategoryEntityMapper).toEntity(category);
        verify(iCategoryRepository).save(categoryEntity);
        verify(iCategoryEntityMapper).toCategory(savedCategoryEntity);
    }

    @Test
    @DisplayName("Check existence of a category by name when it exists")
    void existsByName_shouldReturnTrueIfCategoryExists() {
        String name = "Test Category";

        when(iCategoryRepository.findByName(name)).thenReturn(Optional.of(new CategoryEntity()));

        boolean result = categoryJpaAdapter.existsByName(name);

        assertTrue(result);
        verify(iCategoryRepository).findByName(name);
    }

    @Test
    @DisplayName("Check existence of a category by name when it does not exist")
    void existsByName_shouldReturnFalseIfCategoryDoesNotExist() {
        String name = "Nonexistent Category";

        when(iCategoryRepository.findByName(name)).thenReturn(Optional.empty());

        boolean result = categoryJpaAdapter.existsByName(name);

        assertFalse(result);
        verify(iCategoryRepository).findByName(name);
    }
    @Test
    @DisplayName("Test listCategory returns a paginated list of Category")
    void testListCategory() {

        Category category = new Category(1L, "Category Name", "Category Description");
        CategoryEntity categoriEntity = new CategoryEntity();
        List<CategoryEntity> categoriEntities = List.of(categoriEntity);
        Page<CategoryEntity> categoryPage = new PageImpl<>(categoriEntities, PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name")), 1);

        when(iCategoryRepository.findAll(any(PageRequest.class))).thenReturn(categoryPage);
        when(iCategoryEntityMapper.toCategory(categoriEntity)).thenReturn(category);

        PaginatedResult<Category> result = categoryJpaAdapter.getCategories(0, 10, "name", true);

        assertEquals(1, result.getContent().size(), "The content size should be 1");
        assertEquals(category, result.getContent().get(0), "The article should match");
        assertEquals(0, result.getPageNumber(), "The page number should be 0");
        assertEquals(10, result.getPageSize(), "The page size should be 10");
        assertEquals(1, result.getTotalElements(), "The total elements should be 1");
    }



    @Test
    @DisplayName("Retrieve categories by a list of IDs")
    void getCategoriesByIds_shouldReturnCategoriesByIds() {
        List<Long> ids = List.of(1L, 2L);
        CategoryEntity categoryEntity = new CategoryEntity();
        List<CategoryEntity> categoryEntities = List.of(categoryEntity);

        when(iCategoryRepository.findAllById(ids)).thenReturn(categoryEntities);
        when(iCategoryEntityMapper.toCategory(categoryEntity))
                .thenReturn(new Category(1L, "Test Category", "Category Description"));

        List<Category> result = categoryJpaAdapter.getCategoriesByIds(ids);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Test Category", result.get(0).getName());
        assertEquals("Category Description", result.get(0).getDescription());
        verify(iCategoryRepository).findAllById(ids);
        verify(iCategoryEntityMapper).toCategory(categoryEntity);
    }



}
