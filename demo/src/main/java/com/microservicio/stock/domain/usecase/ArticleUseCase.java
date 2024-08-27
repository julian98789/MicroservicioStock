package com.microservicio.stock.domain.usecase;

import com.microservicio.stock.domain.api.IArticleServicePort;
import com.microservicio.stock.domain.exception.custom.NameAlreadyExistsException;
import com.microservicio.stock.domain.exception.custom.RepeatedCategoryException;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.spi.IArticlePersistencePort;
import com.microservicio.stock.domain.util.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArticleUseCase implements IArticleServicePort {

    private final IArticlePersistencePort articlePersistencePort;

    public ArticleUseCase(IArticlePersistencePort articlePersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
    }

    @Override
    public Article saveArticle(Article article) {
        if (articlePersistencePort.existsByName(article.getName())) {
            throw new NameAlreadyExistsException(Util.NAME_ALREADY_EXISTS);
        }
        return articlePersistencePort.saveArticle(article);
    }

    @Override
    public List<Article> getArticles(int page, int size, String sort, boolean ascending) {
        return articlePersistencePort.getArticles(page, size, sort, ascending);
    }

}
