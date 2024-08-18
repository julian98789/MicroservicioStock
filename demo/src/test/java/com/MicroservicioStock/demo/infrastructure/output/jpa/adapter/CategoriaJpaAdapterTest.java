package com.MicroservicioStock.demo.infrastructure.output.jpa.adapter;

import com.MicroservicioStock.demo.domain.model.Categori;
import com.MicroservicioStock.demo.infrastructure.exception.custom.CategoriAlreadyExistsException;
import com.MicroservicioStock.demo.infrastructure.output.jpa.entity.CategoriEntity;
import com.MicroservicioStock.demo.infrastructure.output.jpa.mapper.CategoriEntityMapper;
import com.MicroservicioStock.demo.infrastructure.output.jpa.repository.ICategoriRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoriaJpaAdapterTest {

    @Mock
    private ICategoriRepository iCategoriRepository;

    @Mock
    private CategoriEntityMapper categoriEntityMapper;

    @InjectMocks
    private CategoriaJpaAdapter categoriaJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Debe lanzar excepción cuando la categoría ya existe")
    void saveCategori_ShouldThrowExceptionWhenCategoryAlreadyExists() {
        // Dado
        Categori categori = new Categori(1L, "Electronics", "Devices and gadgets");

        //cuando
        when(iCategoriRepository.findByName(categori.getName())).thenReturn(Optional.of(new CategoriEntity()));

        // Entonces
        assertThrows(CategoriAlreadyExistsException.class, () -> categoriaJpaAdapter.saveCategori(categori));
    }

    @Test
    @DisplayName("Debe convertir y guardar la entidad")
    void saveCategori_ShouldConvertAndSaveEntity() {
        // Dado
        Categori categori = new Categori(1L, "Electronics", "Devices and gadgets");
        CategoriEntity categoriEntity = new CategoriEntity(1L, "Electronics", "Devices and gadgets");
        CategoriEntity savedEntity = new CategoriEntity(1L, "Electronics", "Devices and gadgets");

        // Cuando
        when(iCategoriRepository.findByName(categori.getName())).thenReturn(Optional.empty());
        when(categoriEntityMapper.toEntity(categori)).thenReturn(categoriEntity);
        when(iCategoriRepository.save(categoriEntity)).thenReturn(savedEntity);
        when(categoriEntityMapper.toCategori(savedEntity)).thenReturn(categori);


        Categori result = categoriaJpaAdapter.saveCategori(categori);

        // Entonces
        verify(iCategoriRepository).findByName(categori.getName());
        verify(categoriEntityMapper).toEntity(categori);
        verify(iCategoriRepository).save(categoriEntity);
        verify(categoriEntityMapper).toCategori(savedEntity);
        assertEquals(categori, result);
    }
}