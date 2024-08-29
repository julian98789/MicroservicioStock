package com.microservicio.stock.domain.usecase;

import com.microservicio.stock.domain.exception.custom.NameAlreadyExistsException;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.api.IBrandServicePort;
import com.microservicio.stock.domain.spi.IBrandPersistencePort;
import com.microservicio.stock.domain.util.Util;

import java.util.List;

public class BrandUseCase implements IBrandServicePort {

    private final IBrandPersistencePort iBrandPersistencePort;

    public BrandUseCase(IBrandPersistencePort iBrandPersistencePort) {
        this.iBrandPersistencePort = iBrandPersistencePort;
    }

    @Override
    public Brand saveBrand(Brand brand) {
        if (iBrandPersistencePort.existsByName(brand.getName())) {
            throw new NameAlreadyExistsException(Util.BRAND_NAME_ALREADY_EXISTS);
        }
        return iBrandPersistencePort.saveBrand(brand);
    }

    @Override
    public boolean existsByName(String name) {
        return iBrandPersistencePort.existsByName(name);
    }

    @Override
    public List<Brand> getBrands(int page, int size, String sort, boolean ascending) {
        return iBrandPersistencePort.getBrands(page, size, sort, ascending);
    }

}
