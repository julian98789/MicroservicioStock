package com.MicroservicioStock.demo.domain.api;

import com.MicroservicioStock.demo.domain.model.Brand;

import java.util.List;

public interface IBrandServicePort {
    Brand saveBrand(Brand brand);

    boolean existsByName(String name);

    List<Brand> getBrands(int page, int size, String sort, boolean ascending);
}
