package com.MicroservicioStock.demo.application.handler.brandHandler;

import com.MicroservicioStock.demo.application.dto.brandDto.BrandRequest;
import com.MicroservicioStock.demo.application.dto.brandDto.BrandResponse;

import java.util.List;

public interface IBrandHandler {
    BrandResponse savedBrand(BrandRequest brandRequest);
    List<BrandResponse> getBrands(int page, int size, String sort, boolean ascending);
}
