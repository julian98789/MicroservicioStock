package com.microservicio.stock.domain.usecase;

import com.microservicio.stock.domain.api.IArticleServicePort;
import com.microservicio.stock.domain.exception.custom.ValidationExceptions;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.spi.IArticlePersistencePort;

import java.util.List;

public class ArticleUseCase implements IArticleServicePort {

    private final IArticlePersistencePort articlePersistencePort;

    public ArticleUseCase(IArticlePersistencePort articlePersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
    }

    @Override
    public Article saveArticle(Article article) {
        validateArticle(article);
        return articlePersistencePort.saveArticle(article);
    }

    @Override
    public List<Article> getArticles(int page, int size, String sort, boolean ascending) {
        return articlePersistencePort.getArticles(page, size, sort, ascending);
    }


    private void validateArticle(Article article) {
        if (article.getCategories() == null || article.getCategories().isEmpty()) {
            throw new ValidationExceptions("El artículo debe tener al menos una categoría.");
        }
        if (article.getCategories().size() > 3) {
            throw new ValidationExceptions("El artículo no puede tener más de tres categorías.");
        }

        if (article.getName() == null || article.getName().isEmpty()) {
            throw new ValidationExceptions("El nombre del artículo no puede estar vacío.");
        }

    }
}
