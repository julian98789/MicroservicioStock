package com.microservicio.stock.application.handler.articlehandler;

import com.microservicio.stock.application.dto.articledto.ArticleRequest;
import com.microservicio.stock.application.dto.articledto.ArticleResponse;
import com.microservicio.stock.domain.pagination.PaginatedResult;


public interface IArticleHandler {
    ArticleResponse saveArticle(ArticleRequest articleRequest);

    PaginatedResult<ArticleResponse> listArticles(int page, int size, String sortField, boolean ascending);

}
