package com.MicroservicioStock.demo.application.mapper.categoriMappper;

import com.MicroservicioStock.demo.application.dto.categoriDto.CategoriResponse;
import com.MicroservicioStock.demo.domain.model.Categori;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoriResponseMapper {

    CategoriResponse categoriResponseToresponse(Categori categori);
}
