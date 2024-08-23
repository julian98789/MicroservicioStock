package com.MicroservicioStock.demo.application.mapper.brandMappper;

import com.MicroservicioStock.demo.application.dto.brandDto.BrandResponse;
import com.MicroservicioStock.demo.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBrandResponseMappper {

    BrandResponse brandResponseToResponse(Brand brand);
}
