package com.MicroservicioStock.demo.application.mapper;

import com.MicroservicioStock.demo.application.dto.CategoriResponse;
import com.MicroservicioStock.demo.domain.model.Categori;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoriResponseMapper {

    CategoriResponse categoriResponseToresponse(Categori categori);
}
