package com.microservicio.stock.application.handler.brandhandler;

import com.microservicio.stock.application.dto.branddto.BrandRequest;
import com.microservicio.stock.application.dto.branddto.BrandResponse;
import com.microservicio.stock.application.mapper.brandmapper.IBrandRequestMapper;
import com.microservicio.stock.application.mapper.brandmapper.IBrandResponseMapper;
import com.microservicio.stock.domain.api.IBrandServicePort;
import com.microservicio.stock.domain.model.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandHandler implements IBrandHandler {
    private final IBrandServicePort iBrandServicePort;
    private final IBrandRequestMapper iBrandRequestMapper;
    private final IBrandResponseMapper iBrandResponseMapper;

    @Override
    public BrandResponse savedBrand(BrandRequest brandRequest) {
        Brand brand = iBrandRequestMapper.brandRequestToBrand(brandRequest);
        Brand savedBrand = iBrandServicePort.saveBrand(brand);
        return iBrandResponseMapper.brandResponseToResponse(savedBrand);
    }


    @Override
    public List<BrandResponse> getBrands(int page, int size, String sort, boolean ascending) {
         List<Brand> brands = iBrandServicePort.getBrands( page,  size,  sort,  ascending);
         return brands.stream()
                 .map(iBrandResponseMapper::brandResponseToResponse).toList();
    }
}
