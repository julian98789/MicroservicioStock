package com.microservicio.stock.application.handler.articlehandler;

import com.microservicio.stock.application.dto.articledto.ArticleRequest;
import com.microservicio.stock.application.dto.articledto.ArticleResponse;

import java.util.List;

public interface IArticleHandler {
    ArticleResponse saveArticle(ArticleRequest articleRequest);

    List<ArticleResponse> listArticles(int page, int size, String sort, boolean ascending);

}
