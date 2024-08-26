package com.microservicio.stock.domain.usecase;

import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.api.IBrandServicePort;
import com.microservicio.stock.domain.exception.custom.BrandAlreadyExistsException;
import com.microservicio.stock.domain.exception.custom.ValidationExceptions;
import com.microservicio.stock.domain.spi.IBrandPersistencePort;

import java.util.List;

public class BrandUseCase implements IBrandServicePort {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 120;

    private final IBrandPersistencePort iBrandPersistencePort;

    public BrandUseCase(IBrandPersistencePort iBrandPersistencePort) {
        this.iBrandPersistencePort = iBrandPersistencePort;
    }

    @Override
    public Brand saveBrand(Brand brand) {
        validateBrand(brand);
        if (iBrandPersistencePort.existsByName(brand.getName())) {
            throw new BrandAlreadyExistsException("La marca ya existe");
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

    public void validateBrand(Brand brand) {
        if (brand.getName() == null || brand.getName().isEmpty()) {
            throw new ValidationExceptions("The name cannot be empty");
        }
        if (brand.getName().length() > MAX_NAME_LENGTH) {
            throw new ValidationExceptions("The name must not exceed 50 characters");
        }
        if (brand.getDescription() == null || brand.getDescription().isEmpty()) {
            throw new ValidationExceptions("The description cannot be empty");
        }
        if (brand.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            throw new ValidationExceptions("The description must not exceed 120 characters");
        }
    }
}
