package com.microservicio.stock.application.mapper.brandmappper;

import com.microservicio.stock.application.dto.branddto.BrandResponse;
import com.microservicio.stock.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBrandResponseMappper {

    BrandResponse brandResponseToResponse(Brand brand);
}
