package com.MicroservicioStock.demo.infrastructure.output.jpa.adapter;

import com.MicroservicioStock.demo.domain.model.Categori;
import com.MicroservicioStock.demo.domain.exception.custom.CategoriAlreadyExistsException;
import com.MicroservicioStock.demo.infrastructure.output.jpa.entity.CategoriEntity;
import com.MicroservicioStock.demo.infrastructure.output.jpa.mapper.CategoriEntityMapper;
import com.MicroservicioStock.demo.infrastructure.output.jpa.repository.ICategoriRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaJpaAdapterTest {
    private final ICategoriRepository iCategoriRepository = Mockito.mock(ICategoriRepository.class);
    private final CategoriEntityMapper categoriEntityMapper = Mockito.mock(CategoriEntityMapper.class);
    private final CategoriaJpaAdapter categoriaJpaAdapter = new CategoriaJpaAdapter(iCategoriRepository, categoriEntityMapper);

    @Test
    @DisplayName("Successfully save category")
    void testSaveCategori_Success() {
        // Dado
        Categori categori = new Categori(1L, "Electronics", "Devices and gadgets");

        CategoriEntity categoriEntity = new CategoriEntity();
        categoriEntity.setId(1L);
        categoriEntity.setName("Electronics");
        categoriEntity.setDescription("Devices and gadgets");

        when(categoriEntityMapper.toEntity(categori)).thenReturn(categoriEntity);
        when(iCategoriRepository.save(categoriEntity)).thenReturn(categoriEntity);
        when(categoriEntityMapper.toCategori(categoriEntity)).thenReturn(categori);

        // Cuando
        Categori savedCategori = categoriaJpaAdapter.saveCategori(categori);

        // Entonces
        assertNotNull(savedCategori);
        assertEquals("Electronics", savedCategori.getName());
        assertEquals("Devices and gadgets", savedCategori.getDescription());
        verify(iCategoriRepository, times(1)).save(categoriEntity);
    }

    @Test
    @DisplayName("Throw exception if category already exists")
    void testSaveCategori_CategoryAlreadyExists() {
        // Dado
        Categori categori = new Categori(1L, "Electronics", "Devices and gadgets");

        when(iCategoriRepository.findByName(categori.getName())).thenReturn(Optional.of(new CategoriEntity()));

        // Cuando y Entonces
        assertThrows(CategoriAlreadyExistsException.class, () -> {
            if (categoriaJpaAdapter.existsByName(categori.getName())) {
                throw new CategoriAlreadyExistsException("La categoría ya existe");
            }
        });
    }
}
