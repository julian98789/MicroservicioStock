package com.microservicio.stock.application.handler.brandhandler;

import com.microservicio.stock.application.dto.branddto.BrandRequest;
import com.microservicio.stock.application.dto.branddto.BrandResponse;
import com.microservicio.stock.application.mapper.brandmapper.IBrandRequestMapper;
import com.microservicio.stock.application.mapper.brandmapper.IBrandResponseMapper;
import com.microservicio.stock.domain.api.IBrandServicePort;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.util.pagination.PaginatedResult;
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
    public PaginatedResult<BrandResponse> getBrands(int page, int size, String sort, boolean ascending) {
        PaginatedResult<Brand> brands = iBrandServicePort.getBrands(page, size, sort, ascending);

        List<BrandResponse> brandResponses = brands.getContent().stream()
                .map(iBrandResponseMapper::brandResponseToResponse)
                .toList();

        return new PaginatedResult<>(
                brandResponses,
                brands.getPageNumber(),
                brands.getPageSize(),
                brands.getTotalElements()
        );
    }
}
