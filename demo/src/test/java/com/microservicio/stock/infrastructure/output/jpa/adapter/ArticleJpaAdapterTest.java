package com.microservicio.stock.infrastructure.output.jpa.adapter;

import com.microservicio.stock.domain.model.Article;
import com.microservicio.stock.domain.util.pagination.PaginatedResult;
import com.microservicio.stock.infrastructure.output.jpa.entity.ArticleEntity;
import com.microservicio.stock.infrastructure.output.jpa.mapper.IArticleEntityMapper;
import com.microservicio.stock.infrastructure.output.jpa.repository.IArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ArticleJpaAdapterTest {

    @Mock
    private IArticleEntityMapper articleEntityMapper;

    @Mock
    private IArticleRepository articleRepository;

    @InjectMocks
    private ArticleJpaAdapter articleJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test saveArticle saves an article and returns the saved article")
    void testSaveArticle() {
        Article article = new Article(1L, "Article Name", "Article Description", 10, BigDecimal.valueOf(100.00), null, new ArrayList<>());
        ArticleEntity articleEntity = new ArticleEntity();
        ArticleEntity savedArticleEntity = new ArticleEntity();
        when(articleEntityMapper.toEntity(article)).thenReturn(articleEntity);
        when(articleRepository.save(articleEntity)).thenReturn(savedArticleEntity);
        when(articleEntityMapper.toModel(savedArticleEntity)).thenReturn(article);

        Article result = articleJpaAdapter.saveArticle(article);

        assertEquals(article, result);
    }

    @Test
    @DisplayName("Test listArticles returns a paginated list of articles")
    void testListArticles() {

        Article article = new Article(1L, "Article Name", "Article Description", 10, BigDecimal.valueOf(100.00), null, new ArrayList<>());
        ArticleEntity articleEntity = new ArticleEntity();
        List<ArticleEntity> articleEntities = List.of(articleEntity);
        Page<ArticleEntity> articlePage = new PageImpl<>(articleEntities, PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name")), 1);

        when(articleRepository.findAll(any(PageRequest.class))).thenReturn(articlePage);
        when(articleEntityMapper.toModel(articleEntity)).thenReturn(article);

        // Act
        PaginatedResult<Article> result = articleJpaAdapter.listArticles(0, 10, "name", true);

        // Assert
        assertEquals(1, result.getContent().size(), "The content size should be 1");
        assertEquals(article, result.getContent().get(0), "The article should match");
        assertEquals(0, result.getPageNumber(), "The page number should be 0");
        assertEquals(10, result.getPageSize(), "The page size should be 10");
        assertEquals(1, result.getTotalElements(), "The total elements should be 1");
    }
    @Test
    @DisplayName("Check existence of a article by name when it exists")
    void testExistsByName_whenArticleExists() {
        String name = "Article Name";
        when(articleRepository.findByName(name)).thenReturn(Optional.of(new ArticleEntity()));

        boolean result = articleJpaAdapter.existsByName(name);

        assertEquals(true, result);
    }

    @Test
    @DisplayName("Check existence of a article by name when it does not exist")
    void testExistsByName_whenArticleDoesNotExist() {
        String name = "Nonexistent Article Name";
        when(articleRepository.findByName(name)).thenReturn(Optional.empty());

        boolean result = articleJpaAdapter.existsByName(name);

        assertEquals(false, result);
    }

}

