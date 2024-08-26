package com.microservicio.stock.domain.spi;

import com.microservicio.stock.domain.model.Article;

import java.util.List;

public interface IArticlePersistencePort {
    Article saveArticle(Article article);
    List<Article> getArticles(int page, int size, String sort, boolean ascending);
    boolean existsByName(String name);

}
