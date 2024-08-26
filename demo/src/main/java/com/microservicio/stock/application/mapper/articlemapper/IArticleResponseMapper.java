package com.microservicio.stock.application.mapper.articlemapper;

import com.microservicio.stock.application.dto.articledto.ArticleResponse;
import com.microservicio.stock.domain.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IArticleResponseMapper {

    @Mapping(target = "categories", source = "categories")
    @Mapping(target = "brand", source = "brand")
    ArticleResponse toArticleResponseDto(Article article);

}
