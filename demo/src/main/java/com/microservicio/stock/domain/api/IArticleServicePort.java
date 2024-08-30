package com.microservicio.stock.domain.api;

import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.util.pagination.PaginatedResult;


public interface IArticleServicePort {
    Article saveArticle(Article article);
    PaginatedResult<Article> listArticles(int page, int size, String sortBy, boolean ascending);
}
