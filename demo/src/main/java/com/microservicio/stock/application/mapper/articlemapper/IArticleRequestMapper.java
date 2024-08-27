package com.microservicio.stock.application.mapper.articlemapper;

import com.microservicio.stock.application.dto.articledto.ArticleRequest;
import com.microservicio.stock.domain.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IArticleRequestMapper {

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "brand", ignore = true)
    Article articleRequestToArticle(ArticleRequest articleRequest);
}
