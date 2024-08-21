package com.MicroservicioStock.demo.infrastructure.output.jpa.adapter;

import com.MicroservicioStock.demo.domain.model.Categori;
import com.MicroservicioStock.demo.domain.spi.ICategoriPersistencePort;
import com.MicroservicioStock.demo.infrastructure.output.jpa.entity.CategoriEntity;
import com.MicroservicioStock.demo.infrastructure.output.jpa.mapper.CategoriEntityMapper;
import com.MicroservicioStock.demo.infrastructure.output.jpa.repository.ICategoriRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<Categori> getCategories(int page, int size, String sort, boolean ascending) {
        Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sort));

        Page<CategoriEntity> categoriEntities = iCategoriRepository.findAll(pageRequest);

        return categoriEntities.stream()
                .map(categoriEntityMapper::toCategori).toList();

    }
}
