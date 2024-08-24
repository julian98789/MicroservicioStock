package com.microservicio.stock.application.handler.brandhandler;

import com.microservicio.stock.application.dto.branddto.BrandRequest;
import com.microservicio.stock.application.dto.branddto.BrandResponse;

import java.util.List;

public interface IBrandHandler {
    BrandResponse savedBrand(BrandRequest brandRequest);
    List<BrandResponse> getBrands(int page, int size, String sort, boolean ascending);
}
