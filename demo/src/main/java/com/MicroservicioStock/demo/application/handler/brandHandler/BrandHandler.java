package com.MicroservicioStock.demo.application.handler.brandHandler;

import com.MicroservicioStock.demo.application.dto.brandDto.BrandRequest;
import com.MicroservicioStock.demo.application.dto.brandDto.BrandResponse;
import com.MicroservicioStock.demo.application.mapper.brandMappper.IBrandRequestMappper;
import com.MicroservicioStock.demo.application.mapper.brandMappper.IBrandResponseMappper;
import com.MicroservicioStock.demo.domain.api.IBrandServicePort;
import com.MicroservicioStock.demo.domain.model.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandHandler implements IBrandHandler {
    private final IBrandServicePort iBrandServicePort;
    private final IBrandRequestMappper iBrandRequestMappper;
    private final IBrandResponseMappper iBrandResponseMappper;

    @Override
    public BrandResponse savedBrand(BrandRequest brandRequest) {
        String name = brandRequest.getName();
        if (iBrandServicePort.existsByName(name)) {
            throw new IllegalArgumentException("Marca con nombre '" + name + "' ya existe.");
        }
        Brand brand = iBrandRequestMappper.brandRequestToBrand(brandRequest);
        Brand savedBrand = iBrandServicePort.saveBrand(brand);
        return iBrandResponseMappper.brandResponseToResponse(savedBrand);
    }


    @Override
    public List<BrandResponse> getBrands(int page, int size, String sort, boolean ascending) {
         List<Brand> brands = iBrandServicePort.getBrands( page,  size,  sort,  ascending);
         return brands.stream()
                 .map(iBrandResponseMappper::brandResponseToResponse).toList();
    }
}
