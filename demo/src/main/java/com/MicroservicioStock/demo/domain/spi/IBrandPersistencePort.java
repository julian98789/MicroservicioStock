package com.MicroservicioStock.demo.domain.spi;

import com.MicroservicioStock.demo.domain.model.Brand;

import java.util.List;

public interface IBrandPersistencePort {
    Brand saveBrand(Brand brand);

    boolean existsByName(String name);

    List<Brand> getBrands(int page, int size, String sort, boolean ascending);
}
