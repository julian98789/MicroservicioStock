package com.microservicio.stock.domain.usecase;

import com.microservicio.stock.domain.api.IArticleServicePort;
import com.microservicio.stock.domain.exception.custom.NameAlreadyExistsException;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.pagination.PaginatedResult;
import com.microservicio.stock.domain.spi.IArticlePersistencePort;
import com.microservicio.stock.domain.util.Util;

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
    public PaginatedResult<Article> listArticles(int page, int size, String sortBy, boolean ascending) {
        return articlePersistencePort.listArticles(page, size, sortBy, ascending);
    }


}
