package com.microservicio.stock.infrastructure.output.jpa.adapter;

import com.microservicio.stock.infrastructure.output.jpa.entity.BrandEntity;
import com.microservicio.stock.infrastructure.output.jpa.mapper.IBrandEntityMapper;
import com.microservicio.stock.infrastructure.output.jpa.repository.IBrandRepository;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.spi.IBrandPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
        Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sort));

        Page<BrandEntity> brandEntities = iBrandRepository.findAll(pageRequest);

        return brandEntities.stream()
                .map(iBrandEntityMapper::toBrand).toList();
    }
}
