package com.microservicio.stock.domain.usecase;

import com.microservicio.stock.domain.api.IArticleServicePort;
import com.microservicio.stock.domain.exception.custom.NameAlreadyExistsException;
import com.microservicio.stock.domain.exception.custom.RepeatedCategoryException;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.spi.IArticlePersistencePort;

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
        validateUniqueCategories(article.getCategories());
        if (articlePersistencePort.existsByName(article.getName())) {
            throw new NameAlreadyExistsException("el article '" +article.getName()+"' ya existe");
        }
        return articlePersistencePort.saveArticle(article);
    }

    @Override
    public List<Article> getArticles(int page, int size, String sort, boolean ascending) {
        return articlePersistencePort.getArticles(page, size, sort, ascending);
    }

    private void validateUniqueCategories(List<Category> categories) {
        Set<Category> uniqueCategories = new HashSet<>(categories);

        if (uniqueCategories.size() != categories.size()) {
            throw new RepeatedCategoryException("La lista de categorías contiene elementos duplicados.");
        }
    }
}
