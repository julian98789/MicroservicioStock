package com.MicroservicioStock.demo.infrastructure.output.jpa.adapter;

import com.MicroservicioStock.demo.domain.model.Categori;
import com.MicroservicioStock.demo.domain.spi.ICategoriPersistencePort;
import com.MicroservicioStock.demo.infrastructure.exception.custom.CategoriAlreadyExistsException;
import com.MicroservicioStock.demo.infrastructure.output.jpa.mapper.CategoriEntityMapper;
import com.MicroservicioStock.demo.infrastructure.output.jpa.repository.ICategoriRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoriaJpaAdapter implements ICategoriPersistencePort {

    private final ICategoriRepository iCategoriRepository;
    private final CategoriEntityMapper categoriEntityMapper;


    @Override
    public void saveCategori(Categori categori) {
        if (iCategoriRepository.findByName(categori.getName()).isPresent()){
            throw new CategoriAlreadyExistsException("La categoria "+categori.getName()+" ya existe");
        }
        iCategoriRepository.save(categoriEntityMapper.toEntity(categori));
    }
}
