package com.MicroservicioStock.demo.infrastructure.output.jpa.adapter;

import com.MicroservicioStock.demo.domain.model.Categori;
import com.MicroservicioStock.demo.domain.spi.ICategoriPersistencePort;
import com.MicroservicioStock.demo.domain.exception.custom.CategoriAlreadyExistsException;
import com.MicroservicioStock.demo.infrastructure.output.jpa.entity.CategoriEntity;
import com.MicroservicioStock.demo.infrastructure.output.jpa.mapper.CategoriEntityMapper;
import com.MicroservicioStock.demo.infrastructure.output.jpa.repository.ICategoriRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CategoriaJpaAdapter implements ICategoriPersistencePort {

    private final ICategoriRepository iCategoriRepository;
    private final CategoriEntityMapper categoriEntityMapper;


    @Override
    public Categori saveCategori(Categori categori) {
        CategoriEntity categoriEntity = categoriEntityMapper.toEntity(categori);
        CategoriEntity savedEntity = iCategoriRepository.save(categoriEntity);
        return categoriEntityMapper.toCategori(savedEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return iCategoriRepository.findByName(name).isPresent();
    }
}
