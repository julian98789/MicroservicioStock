package com.microservicio.stock.application.mapper.categorymapper;

import com.microservicio.stock.application.dto.categorydto.CategoryResponse;
import com.microservicio.stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryResponseMapper {

    CategoryResponse categoryResponseToResponse(Category category);
}
