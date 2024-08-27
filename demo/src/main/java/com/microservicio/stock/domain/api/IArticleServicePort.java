package com.microservicio.stock.domain.api;

import com.microservicio.stock.domain.model.Article;

import java.util.List;

public interface IArticleServicePort {
    Article saveArticle(Article article);
    List<Article> getArticles(int page, int size, String sort, boolean ascending);
}
