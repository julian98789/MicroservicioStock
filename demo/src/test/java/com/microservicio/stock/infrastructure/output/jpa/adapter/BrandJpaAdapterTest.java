package com.microservicio.stock.infrastructure.output.jpa.adapter;

import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.infrastructure.output.jpa.entity.BrandEntity;
import com.microservicio.stock.infrastructure.output.jpa.mapper.IBrandEntityMapper;
import com.microservicio.stock.infrastructure.output.jpa.repository.IBrandRepository;
import jakarta.persistence.EntityNotFoundException;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BrandJpaAdapterTest {

    @Mock
    private IBrandRepository iBrandRepository;

    @Mock
    private IBrandEntityMapper iBrandEntityMapper;

    @InjectMocks
    private BrandJpaAdapter brandJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("Save a brand and return the saved brand")
    void saveBrand_shouldSaveAndReturnBrand() {
        Long id = 1L;
        String name = "Test Brand";
        String description = "Test Description";
        Brand brand = new Brand(id, name, description);
        BrandEntity brandEntity = new BrandEntity();
        BrandEntity savedBrandEntity = new BrandEntity();
        savedBrandEntity.setId(id);
        savedBrandEntity.setName(name);
        savedBrandEntity.setDescription(description);

        when(iBrandEntityMapper.toEntity(brand)).thenReturn(brandEntity);
        when(iBrandRepository.save(brandEntity)).thenReturn(savedBrandEntity);
        when(iBrandEntityMapper.toBrand(savedBrandEntity)).thenReturn(new Brand(id, name, description));

        Brand result = brandJpaAdapter.saveBrand(brand);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(name, result.getName());
        assertEquals(description, result.getDescription());
        verify(iBrandEntityMapper).toEntity(brand);
        verify(iBrandRepository).save(brandEntity);
        verify(iBrandEntityMapper).toBrand(savedBrandEntity);
    }

    @Test
    @DisplayName("Check existence of a brand by name when it exists")
    void existsByName_shouldReturnTrueIfBrandExists() {
        String name = "Existing Brand";

        when(iBrandRepository.findByName(name)).thenReturn(Optional.of(new BrandEntity()));

        boolean result = brandJpaAdapter.existsByName(name);

        assertTrue(result);
        verify(iBrandRepository).findByName(name);
    }

    @Test
    @DisplayName("Check existence of a brand by name when it does not exist")
    void existsByName_shouldReturnFalseIfBrandDoesNotExist() {
        String name = "Nonexistent Brand";

        when(iBrandRepository.findByName(name)).thenReturn(Optional.empty());

        boolean result = brandJpaAdapter.existsByName(name);

        assertFalse(result);
        verify(iBrandRepository).findByName(name);
    }

    @Test
    @DisplayName("Retrieve paginated brands")
    void getBrands_shouldReturnPaginatedBrands() {
        int page = 0;
        int size = 10;
        String sort = "name";
        boolean ascending = true;

        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(1L);
        brandEntity.setName("Test Brand");
        brandEntity.setDescription("Test Description");

        List<BrandEntity> brandEntities = List.of(brandEntity);
        Page<BrandEntity> pageResult = new PageImpl<>(brandEntities);

        when(iBrandRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sort))))
                .thenReturn(pageResult);
        when(iBrandEntityMapper.toBrand(brandEntity))
                .thenReturn(new Brand(1L, "Test Brand", "Test Description"));

        List<Brand> result = brandJpaAdapter.getBrands(page, size, sort, ascending);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Test Brand", result.get(0).getName());
        assertEquals("Test Description", result.get(0).getDescription());
        verify(iBrandRepository).findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sort)));
        verify(iBrandEntityMapper).toBrand(brandEntity);
    }

    @Test
    @DisplayName("Retrieve a brand by its ID")
    void getBrandById_shouldReturnBrandIfExists() {
        Long id = 1L;
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(id);
        brandEntity.setName("Test Brand");
        brandEntity.setDescription("Test Description");

        Brand brand = new Brand(id, "Test Brand", "Test Description");

        when(iBrandRepository.findById(id)).thenReturn(Optional.of(brandEntity));
        when(iBrandEntityMapper.toBrand(brandEntity)).thenReturn(brand);

        Brand result = brandJpaAdapter.getBrandById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Test Brand", result.getName());
        assertEquals("Test Description", result.getDescription());
        verify(iBrandRepository).findById(id);
        verify(iBrandEntityMapper).toBrand(brandEntity);
    }

    @Test
    @DisplayName("Retrieve a brand by its ID should throw exception if not found")
    void getBrandById_shouldThrowExceptionIfNotFound() {
        Long id = 1L;

        when(iBrandRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> brandJpaAdapter.getBrandById(id));
        verify(iBrandRepository).findById(id);
    }
}