package com.MicroservicioStock.demo.application.mapper;

import com.MicroservicioStock.demo.application.dto.CategoriRequest;
import com.MicroservicioStock.demo.domain.model.Categori;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface CategoriRequestMappper {

    Categori categoriRequestTocategori(CategoriRequest categoriRequest);
}
