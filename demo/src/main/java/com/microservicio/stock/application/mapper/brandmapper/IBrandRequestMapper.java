package com.microservicio.stock.application.mapper.brandmapper;

import com.microservicio.stock.application.dto.branddto.BrandRequest;
import com.microservicio.stock.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBrandRequestMapper {

    Brand brandRequestToBrand(BrandRequest brandRequest);

}
