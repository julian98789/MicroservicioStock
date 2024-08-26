package com.microservicio.stock.infrastructure.output.jpa.mapper;

import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.infrastructure.output.jpa.entity.ArticleEntity;
import com.microservicio.stock.infrastructure.output.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring",
        uses = {IBrandEntityMapper.class, CategoryEntityMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IArticleEntityMapper {

    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "categories", source = "categories")
    ArticleEntity toEntity(Article article);

    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "categories", source = "categories")
    Article toModel(ArticleEntity articleEntity);

    default List<Category> toCategoryModels(List<CategoryEntity> categoryEntities, CategoryEntityMapper categoryEntityMapper) {
        return categoryEntities.stream()
                .map(categoryEntityMapper::toCategory)
                .collect(Collectors.toList());
    }

    default List<CategoryEntity> toCategoryEntities(List<Category> categories, CategoryEntityMapper categoryEntityMapper) {
        return categories.stream()
                .map(categoryEntityMapper::toEntity)
                .collect(Collectors.toList());
    }


}
