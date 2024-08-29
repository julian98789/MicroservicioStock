package com.microservicio.stock.application.handler.brandhandler;

import com.microservicio.stock.application.dto.branddto.BrandRequest;
import com.microservicio.stock.application.dto.branddto.BrandResponse;
import com.microservicio.stock.domain.util.pagination.PaginatedResult;

import java.util.List;

public interface IBrandHandler {
    BrandResponse savedBrand(BrandRequest brandRequest);
    PaginatedResult<BrandResponse> getBrands(int page, int size, String sort, boolean ascending);
}
