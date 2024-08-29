package com.microservicio.stock.domain.spi;

import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.util.pagination.PaginatedResult;


public interface IArticlePersistencePort {
    Article saveArticle(Article article);
    PaginatedResult<Article> listArticles(int page, int size, String sortBy, boolean ascending);
    boolean existsByName(String name);

}
