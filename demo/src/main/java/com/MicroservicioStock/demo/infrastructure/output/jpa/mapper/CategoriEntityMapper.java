package com.MicroservicioStock.demo.infrastructure.output.jpa.mapper;

import com.MicroservicioStock.demo.domain.model.Categori;
import com.MicroservicioStock.demo.infrastructure.output.jpa.entity.CategoriEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface CategoriEntityMapper {

    CategoriEntity toEntity(Categori categori);

    Categori toCategori(CategoriEntity categoriEntity);
}
