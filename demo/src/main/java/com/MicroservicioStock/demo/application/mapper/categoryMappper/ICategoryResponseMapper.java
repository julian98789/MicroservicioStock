package com.MicroservicioStock.demo.application.mapper.categoryMappper;

import com.MicroservicioStock.demo.application.dto.categoryDto.CategoryResponse;
import com.MicroservicioStock.demo.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryResponseMapper {

    CategoryResponse categoryResponseToResponse(Category category);
}
