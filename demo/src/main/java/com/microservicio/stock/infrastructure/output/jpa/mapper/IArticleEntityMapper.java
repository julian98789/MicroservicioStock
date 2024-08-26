package com.microservicio.stock.infrastructure.output.jpa.mapper;

import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.infrastructure.output.jpa.entity.ArticleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;



@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IArticleEntityMapper {

    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "categories", source = "categories")
    ArticleEntity toEntity(Article article);

    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "categories", source = "categories")
    Article toModel(ArticleEntity articleEntity);


}
