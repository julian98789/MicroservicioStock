package com.microservicio.stock.domain.usecase;

import com.microservicio.stock.domain.exception.custom.NameAlreadyExistsException;
import com.microservicio.stock.domain.exception.custom.RepeatedCategoryException;
import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.model.Brand;
import com.microservicio.stock.domain.model.Category;
import com.microservicio.stock.domain.spi.IArticlePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleUseCaseTest {

    @Mock
    private IArticlePersistencePort articlePersistencePort;

    @InjectMocks
    private ArticleUseCase articleUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testSaveArticleWithUniqueCategories() {
        // Dado
        Category category1 = new Category(1L, "Category1","Description");
        Category category2 = new Category(2L, "Category2","Description");
        Brand brand = new Brand(1L,"Brand","Description");

        Article article = new Article(
                1L,
                "Unique Article",
                "Description",
                10,
                BigDecimal.valueOf(100.0),
                brand,
                Arrays.asList(category1, category2)
        );


        when(articlePersistencePort.existsByName(article.getName())).thenReturn(false);
        when(articlePersistencePort.saveArticle(article)).thenReturn(article);

        // Cuando
        Article savedArticle = articleUseCase.saveArticle(article);

        // Entonces
        verify(articlePersistencePort, times(1)).saveArticle(article);
        assert(savedArticle).equals(article);
    }

    @Test
     void testSaveArticleWithRepeatedCategories() {
        // Dado
        Category category = new Category(1L, "Category","Description");
        Brand brand = new Brand(1L, "Brand1","Description");

        Article article = new Article(
                null,
                "Unique Article",
                "Description",
                10,
                BigDecimal.valueOf(100.0),
                brand,
                Arrays.asList(category, category, category)
        );

        when(articlePersistencePort.existsByName(article.getName())).thenReturn(false);

        // Cuando / Entonces
        assertThrows(RepeatedCategoryException.class, () -> articleUseCase.saveArticle(article));
        verify(articlePersistencePort, never()).saveArticle(any());
    }

    @Test
     void testSaveArticleWithExistingName() {
        // Dado
        Category category1 = new Category(1L, "Category1","Description");
        Brand brand = new Brand(1L, "Brand1","Description");

        Article article = new Article(
                null,
                "Existing Article",
                "Description",
                10,
                BigDecimal.valueOf(100.0),
                brand,
                Collections.singletonList(category1)
        );

        when(articlePersistencePort.existsByName(article.getName())).thenReturn(true);

        // Cuando / Entonces
        assertThrows(NameAlreadyExistsException.class, () -> articleUseCase.saveArticle(article));
        verify(articlePersistencePort, never()).saveArticle(any());
    }

}