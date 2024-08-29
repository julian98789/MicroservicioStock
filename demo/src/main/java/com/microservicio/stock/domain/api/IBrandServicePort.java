package com.microservicio.stock.domain.api;

import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.util.pagination.PaginatedResult;

import java.util.List;

public interface IBrandServicePort {
    Brand saveBrand(Brand brand);

    boolean existsByName(String name);

    PaginatedResult<Brand> getBrands(int page, int size, String sort, boolean ascending);
}
