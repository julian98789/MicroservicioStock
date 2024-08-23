package com.MicroservicioStock.demo.infrastructure.output.jpa.adapter;

import com.MicroservicioStock.demo.domain.model.Brand;
import com.MicroservicioStock.demo.domain.spi.IBrandPersistencePort;
import com.MicroservicioStock.demo.infrastructure.output.jpa.entity.BrandEntity;
import com.MicroservicioStock.demo.infrastructure.output.jpa.mapper.IBrandEntityMapper;
import com.MicroservicioStock.demo.infrastructure.output.jpa.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BrandJpaAdapter implements IBrandPersistencePort {

    private final IBrandRepository iBrandRepository;
    private final IBrandEntityMapper iBrandEntityMapper;

    @Override
    public Brand saveBrand(Brand brand) {
        BrandEntity brandEntity = iBrandEntityMapper.toEntity(brand);
        BrandEntity savedEntity = iBrandRepository.save(brandEntity);
        return iBrandEntityMapper.toBrand(savedEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return iBrandRepository.findByName(name).isPresent();
    }

    @Override
    public List<Brand> getBrands(int page, int size, String sort, boolean ascending) {
        return List.of();
    }
}
